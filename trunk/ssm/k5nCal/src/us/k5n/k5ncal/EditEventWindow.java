/*
 * k5nCal - Java Swing Desktop Calendar App
 * Copyright (C) 2005-2007 Craig Knudsen, craig@k5n.us
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package us.k5n.k5ncal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import us.k5n.ical.Categories;
import us.k5n.ical.Constants;
import us.k5n.ical.Date;
import us.k5n.ical.Description;
import us.k5n.ical.Event;
import us.k5n.ical.Location;
import us.k5n.ical.Rrule;
import us.k5n.ical.Sequence;
import us.k5n.ical.Summary;
import us.k5n.ical.URL;
import us.k5n.ical.Uid;
import us.k5n.k5ncal.data.Calendar;
import us.k5n.k5ncal.data.DataFile;
import us.k5n.k5ncal.data.HttpClient;
import us.k5n.k5ncal.data.HttpClientStatus;
import us.k5n.k5ncal.data.Repository;

import com.toedter.calendar.JDateChooser;

/**
 * Create a Event entry edit window.
 * 
 * @author Craig Knudsen, craig@k5n.us
 * @version $Id: EditEventWindow.java,v 1.8 2008/01/19 16:28:38 cknudsen Exp $
 */
public class EditEventWindow extends JDialog implements Constants,
    ComponentListener {
	Repository repo;
	Event event;
	Calendar selectedCalendar;
	Sequence seq = null;
	JFrame parent;
	JTextField subject;
	JCheckBox allDay;
	JDateChooser dateChooser;
	JLabel timeAt;
	JTextField timeHour;
	JLabel timeSep;
	JTextField timeMinute;
	ToggleLabel ampm;
	JTextField categories;
	JTextField url;
	JTextField location;
	JComboBox status;
	JComboBox calendar;
	JLabel startDate;
	JComboBox repeatType;
	JTextArea description;
	AppPreferences prefs;
	private boolean newEvent = true;
	private static final int REPEAT_NONE = 0, REPEAT_DAILY = 1,
	    REPEAT_WEEKLY = 2, REPEAT_MONTHLY = 3, REPEAT_YEARLY = 4,
	    REPEAT_CUSTOM = 5;

	class IntegerChoice {
		String label;
		int value;

		public IntegerChoice(String label, int value) {
			this.label = label;
			this.value = value;
		}

		public String toString () {
			return label;
		}
	}

	/**
	 * Create a new event window for the specified date.
	 * 
	 * @param parent
	 * @param repo
	 * @param date
	 * @param selectedCalendar
	 */
	public EditEventWindow(JFrame parent, Repository repo, Date date,
	    Calendar selectedCalendar) {
		this ( parent, repo, null, date, selectedCalendar );
	}

	/**
	 * Edit the specified event.
	 * 
	 * @param parent
	 * @param repo
	 * @param event
	 * @param selectedCalendar
	 */
	public EditEventWindow(JFrame parent, Repository repo, Event event,
	    Calendar selectedCalendar) {
		this ( parent, repo, event, null, selectedCalendar );
	}

	private EditEventWindow(JFrame parent, Repository repo, Event event,
	    Date date, Calendar selectedCalendar) {
		super ( parent );
		prefs = AppPreferences.getInstance ();
		super.setSize ( prefs.getEditWindowWidth (), prefs.getEditWindowHeight () );
		super.setLocation ( prefs.getEditWindowX (), prefs.getEditWindowY () );
		// TODO: don't make this modal once we add code to check
		// things like deleting this entry in the main window, etc.
		// super.setModal ( true );
		setDefaultCloseOperation ( JDialog.DISPOSE_ON_CLOSE );

		this.parent = parent;
		this.repo = repo;
		this.event = event;
		this.selectedCalendar = selectedCalendar;

		if ( this.event == null ) {
			newEvent = true;
			if ( date == null )
				this.event = new Event ( "", "", Date.getCurrentDateTime ( "DTSTART" ) );
			else {
				date.setName ( "DTSTART" );
				this.event = new Event ( "", "", date );
			}
		} else {
			newEvent = false;
			// Create an updated sequence number for use only if we save
			// (So don't put it in the original Event object yet)
			if ( this.event.getSequence () == null )
				seq = new Sequence ( 1 );
			else
				seq = new Sequence ( this.event.getSequence ().getNum () + 1 );
		}
		// Make sure there is a Summary and Description
		if ( this.event.getSummary () == null )
			this.event.setSummary ( new Summary () );
		if ( this.event.getDescription () == null )
			this.event.setDescription ( new Description () );
		if ( this.event.getCategories () == null )
			this.event.setCategories ( new Categories () );
		if ( this.event.getLocation () == null )
			this.event.setLocation ( new Location () );
		if ( this.event.getUrl () == null )
			this.event.setUrl ( new URL () );
		if ( newEvent ) {
			Uid uid = new Uid ();
			uid.setValue ( us.k5n.ical.Utils.generateUniqueId ( "K5NCAL" ) );
			this.event.setUid ( uid );
		}

		createWindow ();
		setVisible ( true );
		this.addComponentListener ( this );
	}

	private void createWindow () {
		this.getContentPane ().setLayout ( new BorderLayout () );

		JPanel buttonPanel = new JPanel ();
		buttonPanel.setLayout ( new FlowLayout () );
		JButton saveButton = new JButton ( "Save" );
		saveButton.addActionListener ( new ActionListener () {
			public void actionPerformed ( ActionEvent event ) {
				// Save (write file)
				save ();
			}
		} );
		buttonPanel.add ( saveButton );
		JButton cancelButton = new JButton ( "Cancel" );
		cancelButton.addActionListener ( new ActionListener () {
			public void actionPerformed ( ActionEvent event ) {
				close ();
			}
		} );
		buttonPanel.add ( cancelButton );
		getContentPane ().add ( buttonPanel, BorderLayout.SOUTH );

		JPanel allButButtons = new JPanel ();
		allButButtons.setLayout ( new BorderLayout () );
		allButButtons.setBorder ( BorderFactory.createEmptyBorder ( 5, 5, 5, 5 ) );

		JPanel upperPanel = new JPanel ();
		upperPanel.setBorder ( BorderFactory.createEtchedBorder () );
		GridLayout grid = new GridLayout ( 9, 1 );
		grid.setHgap ( 15 );
		grid.setVgap ( 5 );
		upperPanel.setLayout ( grid );
		int[] proportions = { 20, 80 };

		JPanel subjectPanel = new JPanel ();
		subjectPanel.setLayout ( new ProportionalLayout ( proportions,
		    ProportionalLayout.HORIZONTAL_LAYOUT ) );
		JLabel prompt = new JLabel ( "Subject: " );
		prompt.setHorizontalAlignment ( SwingConstants.RIGHT );
		subjectPanel.add ( prompt );
		subject = new JTextField ();
		if ( event != null && event.getSummary () != null )
			subject.setText ( event.getSummary ().getValue () );
		subjectPanel.add ( subject );
		upperPanel.add ( subjectPanel );

		JPanel allDayPanel = new JPanel ();
		allDayPanel.setLayout ( new ProportionalLayout ( proportions,
		    ProportionalLayout.HORIZONTAL_LAYOUT ) );
		prompt = new JLabel ( "All-day: " );
		prompt.setHorizontalAlignment ( SwingConstants.RIGHT );
		allDayPanel.add ( prompt );
		allDay = new JCheckBox ();
		allDay.setHorizontalAlignment ( SwingConstants.LEFT );
		if ( event != null && event.getStartDate () != null ) {
			allDay.setSelected ( event.getStartDate ().isDateOnly () );
		}
		allDay.addActionListener ( new ActionListener () {
			public void actionPerformed ( ActionEvent event ) {
				toggleAllDay ();
			}
		} );
		allDayPanel.add ( allDay );
		upperPanel.add ( allDayPanel );

		JPanel datePanel = new JPanel ();
		datePanel.setLayout ( new ProportionalLayout ( proportions,
		    ProportionalLayout.HORIZONTAL_LAYOUT ) );
		prompt = new JLabel ( "Date: " );
		prompt.setHorizontalAlignment ( SwingConstants.RIGHT );
		datePanel.add ( prompt );
		JPanel subDatePanel = new JPanel ();
		subDatePanel.setLayout ( new FlowLayout ( FlowLayout.LEFT ) );
		dateChooser = new MyDateChooser ();
		dateChooser.setDateFormatString ( "EEE, MMM dd, YYYY" );
		dateChooser.setCalendar ( this.event.getStartDate ().toCalendar () );
		subDatePanel.add ( dateChooser );
		timeAt = new JLabel ( " at " );
		subDatePanel.add ( timeAt );
		// timeHour = new JFormattedTextField ( createFormatter ( "##" ) );
		timeHour = new JTextField ();
		timeHour.setColumns ( 2 );
		subDatePanel.add ( timeHour );
		timeSep = new JLabel ( ":" );
		subDatePanel.add ( timeSep );
		timeMinute = new JFormattedTextField ( createFormatter ( "##" ) );
		timeMinute.setColumns ( 2 );
		subDatePanel.add ( timeMinute );
		String[] ampmStr = { "AM", "PM" };
		ampm = new ToggleLabel ( ampmStr );
		subDatePanel.add ( ampm );
		datePanel.add ( subDatePanel );
		upperPanel.add ( datePanel );
		if ( event.getStartDate () == null || event.getStartDate ().isDateOnly () ) {
			timeHour.setText ( "12" );
			timeMinute.setText ( "00" );
			ampm.setSelected ( "PM" );
		} else {
			int h = event.getStartDate ().getHour ();
			int m = event.getStartDate ().getMinute ();
			h %= 12;
			if ( h == 0 ) {
				timeHour.setText ( "12" );
			} else if ( h < 10 ) {
				timeHour.setText ( "0" + h );
			} else {
				timeHour.setText ( "" + h );
			}
			if ( event.getStartDate ().getHour () < 12 ) {
				ampm.setSelected ( "AM" );
			} else {
				ampm.setSelected ( "PM" );
			}
			timeMinute.setText ( ( m < 10 ? "0" : "" ) + m );
		}

		JPanel repeatPanel = new JPanel ();
		repeatPanel.setLayout ( new ProportionalLayout ( proportions,
		    ProportionalLayout.HORIZONTAL_LAYOUT ) );
		prompt = new JLabel ( "Repeat: " );
		prompt.setHorizontalAlignment ( SwingConstants.RIGHT );
		repeatPanel.add ( prompt );
		JPanel repeatSubPanel = new JPanel ( new BorderLayout () );
		Vector<IntegerChoice> repeatOptions = new Vector<IntegerChoice> ();
		repeatOptions.addElement ( new IntegerChoice ( "None", REPEAT_NONE ) );
		repeatOptions.addElement ( new IntegerChoice ( "Every day", REPEAT_DAILY ) );
		repeatOptions
		    .addElement ( new IntegerChoice ( "Every week", REPEAT_WEEKLY ) );
		repeatOptions.addElement ( new IntegerChoice ( "Every month",
		    REPEAT_MONTHLY ) );
		repeatOptions
		    .addElement ( new IntegerChoice ( "Every year", REPEAT_YEARLY ) );
		// TODO: implement custom recurrence
		// repeatOptions.addElement ( new IntegerChoice ( "Custom...", REPEAT_CUSTOM
		// ) );
		repeatType = new JComboBox ( repeatOptions );
		Rrule rrule = event.getRrule ();
		String error = null;
		if ( rrule == null ) {
			repeatType.setSelectedIndex ( REPEAT_NONE );
		} else {
			switch ( rrule.getFrequency () ) {
				case Rrule.FREQ_DAILY:
					repeatType.setSelectedIndex ( REPEAT_DAILY );
					break;
				case Rrule.FREQ_WEEKLY:
					repeatType.setSelectedIndex ( REPEAT_WEEKLY );
					break;
				case Rrule.FREQ_MONTHLY:
					repeatType.setSelectedIndex ( REPEAT_MONTHLY );
					break;
				case Rrule.FREQ_YEARLY:
					repeatType.setSelectedIndex ( REPEAT_YEARLY );
					break;
				default:
					// TODO: implement hourly, etc.
					error = "Unsupported frequency";
					break;
			}
		}
		// Check for other advanced Rrule options that are not yet supported in our
		// UI.
		if ( error == null && rrule != null ) {
			if ( event.getExceptions () != null && event.getExceptions ().size () > 0 )
				error = "EXDATE not yet supported";
			else if ( event.getRdates () != null && event.getRdates ().size () > 0 )
				error = "RDATE not yet supported";
			else if ( rrule.byhour != null && rrule.byhour.length > 0 )
				error = "BYHOUR not supported";
			else if ( rrule.byminute != null && rrule.byminute.length > 0 )
				error = "BYMINUTE not supported";
			else if ( rrule.bysecond != null && rrule.bysecond.length > 0 )
				error = "BYSECOND not supported";
			else if ( rrule.bymonth != null && rrule.bymonth.length > 0 )
				error = "BYMONTH not supported";
			else if ( rrule.bymonthday != null && rrule.bymonthday.length > 0 )
				error = "BYMONTHDAY not supported";
			else if ( rrule.bysetpos != null && rrule.bysetpos.length > 0 )
				error = "BYSETPOS not supported";
			else if ( rrule.count > 0 )
				error = "COUNT not supported";
			else if ( rrule.interval > 1 )
				error = "Interval not yet supported";
		}
		if ( error != null ) {
			JOptionPane
			    .showMessageDialog (
			        parent,
			        "Warning: The recurrence type of this\nevent is not yet supported\nby k5nCal.  Editing this event\n will result in data loss."
			            + "\n\n" + "Issue: " + error, "Error",
			        JOptionPane.ERROR_MESSAGE );
		}
		repeatSubPanel.add ( repeatType, BorderLayout.WEST );
		repeatPanel.add ( repeatSubPanel );
		upperPanel.add ( repeatPanel );

		JPanel locPanel = new JPanel ();
		locPanel.setLayout ( new ProportionalLayout ( proportions,
		    ProportionalLayout.HORIZONTAL_LAYOUT ) );
		prompt = new JLabel ( "Location: " );
		prompt.setHorizontalAlignment ( SwingConstants.RIGHT );
		locPanel.add ( prompt );
		location = new JTextField ();
		location.setText ( this.event.getLocation ().getValue () );
		locPanel.add ( location );
		upperPanel.add ( locPanel );

		JPanel urlPanel = new JPanel ();
		urlPanel.setLayout ( new ProportionalLayout ( proportions,
		    ProportionalLayout.HORIZONTAL_LAYOUT ) );
		prompt = new JLabel ( "URL: " );
		prompt.setHorizontalAlignment ( SwingConstants.RIGHT );
		urlPanel.add ( prompt );
		url = new JTextField ();
		url.setText ( this.event.getUrl () == null ? "" : this.event.getUrl ()
		    .getValue () );
		urlPanel.add ( url );
		upperPanel.add ( urlPanel );

		JPanel statusPanel = new JPanel ();
		statusPanel.setLayout ( new ProportionalLayout ( proportions,
		    ProportionalLayout.HORIZONTAL_LAYOUT ) );
		prompt = new JLabel ( "Status: " );
		prompt.setHorizontalAlignment ( SwingConstants.RIGHT );
		statusPanel.add ( prompt );
		Vector<IntegerChoice> statusOptions = new Vector<IntegerChoice> ();
		statusOptions.addElement ( new IntegerChoice ( "Confirmed",
		    STATUS_CONFIRMED ) );
		statusOptions.addElement ( new IntegerChoice ( "Tentative",
		    STATUS_TENTATIVE ) );
		statusOptions.addElement ( new IntegerChoice ( "Cancelled",
		    STATUS_CANCELLED ) );
		status = new JComboBox ( statusOptions );
		switch ( event.getStatus () ) {
			case STATUS_CANCELLED:
				status.setSelectedIndex ( 2 );
				break;
			case STATUS_TENTATIVE:
				status.setSelectedIndex ( 1 );
				break;
			case STATUS_CONFIRMED:
			case STATUS_UNDEFINED:
			default:
				status.setSelectedIndex ( 0 );
				break;
		}
		JPanel statusSubPanel = new JPanel ( new BorderLayout () );
		statusSubPanel.add ( status, BorderLayout.WEST );
		statusPanel.add ( statusSubPanel );
		upperPanel.add ( statusPanel );

		JPanel calPanel = new JPanel ();
		calPanel.setLayout ( new ProportionalLayout ( proportions,
		    ProportionalLayout.HORIZONTAL_LAYOUT ) );
		prompt = new JLabel ( "Calendar: " );
		prompt.setHorizontalAlignment ( SwingConstants.RIGHT );
		calPanel.add ( prompt );
		Vector<Calendar> writableCalendars = new Vector<Calendar> ();
		for ( int i = 0; i < this.repo.getCalendars ().size (); i++ ) {
			Calendar c = this.repo.getCalendars ().elementAt ( i );
			if ( c.getType () == Calendar.LOCAL_CALENDAR ) {
				writableCalendars.addElement ( c );
			} else if ( c.getType () == Calendar.REMOTE_ICAL_CALENDAR
			    && c.getCanWrite () ) {
				writableCalendars.addElement ( c );
			}
		}
		// TODO: show error if no local calendars found
		calendar = new JComboBox ( writableCalendars );
		calendar.setRenderer ( new ComboBoxRenderer () );
		if ( selectedCalendar == null )
			calendar.setSelectedIndex ( 0 );
		else
			calendar.setSelectedItem ( selectedCalendar );
		JPanel calSubPanel = new JPanel ( new BorderLayout () );
		calSubPanel.add ( calendar, BorderLayout.WEST );
		calPanel.add ( calSubPanel );
		upperPanel.add ( calPanel );

		JPanel catPanel = new JPanel ();
		catPanel.setLayout ( new ProportionalLayout ( proportions,
		    ProportionalLayout.HORIZONTAL_LAYOUT ) );
		prompt = new JLabel ( "Categories: " );
		prompt.setHorizontalAlignment ( SwingConstants.RIGHT );
		catPanel.add ( prompt );
		categories = new JTextField ();
		if ( event != null && event.getCategories () != null )
			categories.setText ( event.getCategories ().getValue () );
		catPanel.add ( categories );
		upperPanel.add ( catPanel );

		allButButtons.add ( upperPanel, BorderLayout.NORTH );

		// TODO: eventually add some edit buttons/icons here when
		// we support more than plain text.
		JPanel descrPanel = new JPanel ();
		descrPanel.setLayout ( new BorderLayout () );
		description = new JTextArea ();
		description.setLineWrap ( true );
		description.setWrapStyleWord ( true );
		if ( event != null && event.getDescription () != null )
			description.setText ( event.getDescription ().getValue () );
		description.setCaretPosition ( 0 );
		JScrollPane scrollPane = new MyScrollPane ( description );
		descrPanel.add ( scrollPane, BorderLayout.CENTER );
		allButButtons.add ( descrPanel, BorderLayout.CENTER );

		if ( newEvent )
			allDay.setSelected ( false );
		else
			allDay.setSelected ( event.getStartDate ().isDateOnly () );
		toggleAllDay ();

		getContentPane ().add ( allButButtons, BorderLayout.CENTER );
	}

	protected MaskFormatter createFormatter ( String s ) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter ( s );
		} catch ( java.text.ParseException exc ) {
			System.err.println ( "formatter is bad: " + exc.getMessage () );
			System.exit ( -1 );
		}
		return formatter;
	}

	void save () {
		// Verify a calendar was selected
		Calendar c = (Calendar) this.calendar.getSelectedItem ();
		if ( c == null ) {
			JOptionPane.showMessageDialog ( parent, "You must select a calendar.",
			    "Error", JOptionPane.ERROR_MESSAGE );
			return;
		}

		// Verify a valid date was entered
		java.util.Calendar calendar = dateChooser.getCalendar ();
		if ( calendar == null ) {
			JOptionPane.showMessageDialog ( parent,
			    "You have not entered a valid start date.", "Error",
			    JOptionPane.ERROR_MESSAGE );
		}
		this.event.getStartDate ().setYear (
		    calendar.get ( java.util.Calendar.YEAR ) );
		this.event.getStartDate ().setMonth (
		    calendar.get ( java.util.Calendar.MONTH ) + 1 );
		this.event.getStartDate ().setDay (
		    calendar.get ( java.util.Calendar.DAY_OF_MONTH ) );

		// handle repeat type
		switch ( repeatType.getSelectedIndex () ) {
			case REPEAT_NONE:
				this.event.setRrule ( null );
				break;
			case REPEAT_DAILY:
				this.event.setRrule ( new Rrule ( Rrule.FREQ_DAILY ) );
				break;
			case REPEAT_WEEKLY:
				this.event.setRrule ( new Rrule ( Rrule.FREQ_WEEKLY ) );
				break;
			case REPEAT_MONTHLY:
				this.event.setRrule ( new Rrule ( Rrule.FREQ_MONTHLY ) );
				break;
			case REPEAT_YEARLY:
				this.event.setRrule ( new Rrule ( Rrule.FREQ_YEARLY ) );
				break;
			default:
				System.err.println ( "Error: unknown repeat type" );
				return;
		}

		if ( this.allDay.isSelected () ) {
			this.event.getStartDate ().setDateOnly ( true );
		} else {
			this.event.getStartDate ().setDateOnly ( false );
			int h = 0, m = 0;
			try {
				h = Integer.parseInt ( timeHour.getText () );
				m = Integer.parseInt ( timeMinute.getText () );
			} catch ( NumberFormatException e1 ) {
				JOptionPane.showMessageDialog ( parent,
				    "You have not entered a valid start time.", "Error",
				    JOptionPane.ERROR_MESSAGE );
				return;
			}
			if ( h > 23 || m > 59 ) {
				JOptionPane.showMessageDialog ( parent,
				    "You have not entered a valid start time.", "Error",
				    JOptionPane.ERROR_MESSAGE );
				return;
			}
			if ( ampm.getText ().equals ( "AM" ) ) {
				if ( h == 12 )
					h = 0;
			} else {
				// PM
				if ( h < 12 )
					h += 12;
			}
			this.event.getStartDate ().setHour ( h );
			this.event.getStartDate ().setMinute ( m );
			this.event.getStartDate ().setSecond ( 0 );
		}

		// Note: LAST-MODIFIED gets updated by call to saveEvent
		if ( seq != null ) {
			// TODO: some have suggested that the sequence number should
			// only change if the date/time or location is modified.
			event.setSequence ( seq );
			seq = null;
		}
		try {
			this.event.getDescription ().setValue ( description.getText () );
			this.event.getSummary ().setValue ( subject.getText ().trim () );
			String cats = categories.getText ().trim ();
			if ( cats.length () == 0 )
				this.event.setCategories ( null );
			else
				this.event.getCategories ().setValue ( cats );
			String loc = location.getText ().trim ();
			if ( loc.length () == 0 )
				this.event.setLocation ( null );
			else
				this.event.getLocation ().setValue ( loc );
			String urlStr = url.getText ().trim ();
			if ( urlStr.length () == 0 )
				this.event.setUrl ( null );
			else
				this.event.getUrl ().setValue ( urlStr );
			IntegerChoice ic = (IntegerChoice) status.getSelectedItem ();
			this.event.setStatus ( ic.value );

			// Did the event move from one calendar to another?
			if ( c.equals ( this.selectedCalendar ) ) {
				// No, this event is on the same calendar as before.
				// Download the old calendar from the server before we save (if
				// required).
				syncCalendarIfRequired ( c );
				repo.saveEvent ( c, this.event );
				putRemoteCalendarIfRequired ( c );
			} else {
				// New event or event was moved from one calendar to another.
				if ( !this.newEvent ) {
					// Calendar moved from one calendar to another.
					// Delete from old calendar, buy sync with remote first if required.
					syncCalendarIfRequired ( this.selectedCalendar );
					// TODO: if this is remote calendar, we may want to cancel the event
					// rather than delete it.
					repo.deleteEvent ( this.selectedCalendar, this.event );
					// Clear out the user data for the event (where the calendar
					// info is stored.)
					this.event.setUserData ( null );
					putRemoteCalendarIfRequired ( this.selectedCalendar );
				}
				// Download the calendar that the event was moved to (if required).
				syncCalendarIfRequired ( c );
				// Add to new calendar
				repo.saveEvent ( c, this.event );
				putRemoteCalendarIfRequired ( c );
				// If the above call fails, what should we do???
			}
		} catch ( IOException e2 ) {
			// TODO: add error handler that pops up a window here
			e2.printStackTrace ();
		}
		this.dispose ();
	}

	private boolean syncCalendarIfRequired ( Calendar cal ) {
		if ( cal.getUrl () != null && cal.getCanWrite ()
		    && cal.getSyncBeforePublish () ) {
			System.out.println ( "Get remote calendar: " + cal );
			DataFile df = repo.getDataFileForCalendar ( cal );
			File file = new File ( df.getAbsolutePath () + ".sync" );
			HttpClientStatus result = HttpClient.getRemoteCalendar ( cal.getUrl (),
			    cal.getAuthUsername (), cal.getAuthPassword (), file );
			if ( result.getStatus () == HttpClientStatus.HTTP_STATUS_SUCCESS
			    && file.exists () ) {
				// Rename file.
				df.delete ();
				file.renameTo ( df );
				repo.updateCalendar ( df.getParentFile (), cal );
				return true; // success
			} else {
				// Cannot proceed
				showError ( "Error syncing remote calendar\nbefore writing." );
				return false;
			}
		} else {
			// do not need to sync
			return true; // success
		}
	}

	private boolean putRemoteCalendarIfRequired ( Calendar cal ) {
		if ( cal.getUrl () != null && cal.getCanWrite () ) {
			System.out.println ( "put remote calendar: " + cal );
			DataFile df = repo.getDataFileForCalendar ( cal );
			HttpClientStatus result = HttpClient.putRemoteCalendar ( cal.getUrl (),
			    cal.getAuthUsername (), cal.getAuthPassword (), df );
			if ( result.getStatus () == HttpClientStatus.HTTP_STATUS_SUCCESS ) {
				return true; // success
			} else {
				// Cannot proceed
				showError ( "Error writing calendar to server" );
				return false;
			}
		} else {
			// Calendar not writable to server
			return true;
		}
	}

	void toggleAllDay () {
		this.event.getStartDate ().setDateOnly ( allDay.isSelected () );
		// Hide/Unhide the time edit
		timeAt.setVisible ( !allDay.isSelected () );
		timeHour.setVisible ( !allDay.isSelected () );
		timeSep.setVisible ( !allDay.isSelected () );
		timeMinute.setVisible ( !allDay.isSelected () );
		ampm.setVisible ( !allDay.isSelected () );
	}

	void close () {
		// TODO: check for unsaved changes
		this.dispose ();
	}

	void showError ( String message ) {
		System.err.println ( "Error: " + message );
		JOptionPane.showMessageDialog ( parent, message, "Error",
		    JOptionPane.ERROR_MESSAGE );
	}

	public void componentHidden ( ComponentEvent ce ) {
	}

	public void componentShown ( ComponentEvent ce ) {
	}

	// Handle moving of main window
	public void componentMoved ( ComponentEvent ce ) {
		saveWindowPreferences ();
	}

	public void componentResized ( ComponentEvent ce ) {
		saveWindowPreferences ();
	}

	/**
	 * Save current window width, height so we can restore on next run.
	 */
	public void saveWindowPreferences () {
		prefs.setEditWindowWidth ( this.getWidth () );
		prefs.setEditWindowHeight ( this.getHeight () );
		prefs.setEditWindowX ( this.getX () );
		prefs.setEditWindowY ( this.getY () );
	}

}

class ToggleLabel extends JLabel implements MouseListener {
	String[] choices;
	int selected;
	Color fg, mouseOverColor;
	private Cursor defaultCursor = null;
	private Cursor handCursor = null;

	public ToggleLabel(String[] choices) {
		super ( choices[0] );
		this.choices = choices;
		this.selected = 0;
		this.addMouseListener ( this );
		this.fg = this.getForeground ();
		this.mouseOverColor = new Color ( 0, 0, 255 );
	}

	public void setSelected ( String str ) {
		for ( int i = 0; i < choices.length; i++ ) {
			if ( str.equals ( choices[i] ) ) {
				this.selected = i;
				this.setText ( choices[i] );
			}
		}
	}

	public void mousePressed ( MouseEvent e ) {
	}

	public void mouseReleased ( MouseEvent e ) {
	}

	public void mouseClicked ( MouseEvent e ) {
		this.selected++;
		this.selected %= choices.length;
		this.setText ( choices[this.selected] );
	}

	public void mouseEntered ( MouseEvent e ) {
		this.setForeground ( mouseOverColor );
		// change cursor
		if ( this.defaultCursor == null )
			this.defaultCursor = this.getCursor ();
		if ( this.handCursor == null )
			this.handCursor = new Cursor ( Cursor.HAND_CURSOR );
		this.setCursor ( this.handCursor );
	}

	public void mouseExited ( MouseEvent e ) {
		this.setForeground ( fg );
		this.setCursor ( this.defaultCursor );
	}
}

/**
 * Override JDateChooser so that we can expand the minimum width, which normally
 * doesn't leave enough width to display the full text of the date.
 */
class MyDateChooser extends JDateChooser {
	public MyDateChooser() {
		super ();
	}

	public Dimension getPreferredSize () {
		Dimension d = super.getPreferredSize ();
		return new Dimension ( d.width + 10, d.height );
	}
}

/*
 * Override the default ListCellRenderer for the Calendar JComboBox so that we
 * can include a small box icon to the left of the Calendar name that displays
 * the Calendar color.
 */
class ComboBoxRenderer extends JLabel implements ListCellRenderer {

	public ComboBoxRenderer() {
		setOpaque ( true );
		setHorizontalAlignment ( CENTER );
		setVerticalAlignment ( CENTER );
	}

	public Component getListCellRendererComponent ( JList list, Object value,
	    int index, boolean isSelected, boolean cellHasFocus ) {

		if ( isSelected ) {
			setBackground ( list.getSelectionBackground () );
			setForeground ( list.getSelectionForeground () );
		} else {
			setBackground ( list.getBackground () );
			setForeground ( list.getForeground () );
		}

		Calendar c = (Calendar) value;
		setFont ( list.getFont () );
		setText ( c.getName () );
		this.setHorizontalAlignment ( SwingConstants.LEFT );
		setIcon ( Utils.buildColoredIcon ( c.getBackgroundColor (), c
		    .getForegroundColor () ) );

		return this;
	}

	/**
	 * Build an icon that shows the Calendar's colors.
	 * 
	 * @param fill
	 *          The main color (Calendar.bg)
	 * @param border
	 *          The border color (Calendar.fg)
	 * @return The new ImageIcon for the specified colors
	 */
	private ImageIcon buildIcon ( Color fill, Color border ) {
		int WIDTH = 16;
		int HEIGHT = 16;

		BufferedImage bufimage = new BufferedImage ( WIDTH, HEIGHT,
		    BufferedImage.TYPE_INT_ARGB );
		Graphics g = bufimage.getGraphics ();
		g.setColor ( fill );
		g.fillRect ( 0, 0, WIDTH - 1, HEIGHT - 1 );
		// Draw border
		g.setColor ( border );
		g.drawLine ( 0, 0, WIDTH - 1, 0 );
		g.drawLine ( WIDTH - 1, 0, WIDTH - 1, HEIGHT - 1 );
		g.drawLine ( WIDTH - 1, HEIGHT - 1, 0, HEIGHT - 1 );
		g.drawLine ( 0, HEIGHT - 1, 0, 0 );
		g.dispose ();
		return new ImageIcon ( bufimage );
	}
}
