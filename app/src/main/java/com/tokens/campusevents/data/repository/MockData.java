package com.tokens.campusevents.data.repository;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventCategory;
import com.tokens.campusevents.data.model.EventStatus;
import com.tokens.campusevents.data.model.EventUpdate;
import com.tokens.campusevents.data.model.Notification;
import com.tokens.campusevents.data.model.NotificationType;
import com.tokens.campusevents.data.model.Rsvp;
import com.tokens.campusevents.data.model.RsvpStatus;
import com.tokens.campusevents.data.model.UpdateType;
import com.tokens.campusevents.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static User currentUser = new User("user_1", "Ahmed Raza", "ahmed.raza@lums.edu.pk", "Lahore");

    public static List<Event> events = new ArrayList<>();
    public static List<Rsvp> rsvps = new ArrayList<>();
    public static List<Notification> notifications = new ArrayList<>();
    public static List<EventUpdate> eventUpdates = new ArrayList<>();

    static {
        // Events
        events.add(new Event(
                "evt_1", "LUMS MUN 2025",
                "Annual Model United Nations conference featuring delegates from across Pakistan. Join committees on Security Council, ECOSOC, and specialized agencies.",
                EventCategory.SOCIETY, "LUMS Model UN Society", "org_1",
                "LUMS Main Auditorium", "Near Main Gate",
                "March 15, 2025", "9:00 AM", "6:00 PM",
                200, 145, 500, false, EventStatus.LIVE
        ));

        events.add(new Event(
                "evt_2", "Open Mic Night",
                "An evening of music, poetry, and stand-up comedy. Open to all LUMS students. Sign up at the Bee's Nest counter to perform!",
                EventCategory.ARTS, "LUMS Music Society", "org_2",
                "Bee's Nest Cafe", "Behind SDSB Building",
                "March 10, 2025", "7:00 PM", "10:00 PM",
                80, 62, 0, false, EventStatus.LIVE
        ));

        events.add(new Event(
                "evt_3", "TechFest Spring 2025",
                "48-hour hackathon with workshops on AI/ML, Web3, and Cloud Computing. Amazing prizes and networking opportunities with tech industry leaders.",
                EventCategory.TECH, "LUMS Computer Science Society", "org_3",
                "SBASSE Building", "Room 10-301",
                "March 20, 2025", "10:00 AM", "10:00 AM",
                150, 98, 0, false, EventStatus.LIVE
        ));

        events.add(new Event(
                "evt_4", "Business Case Competition",
                "Inter-university case competition sponsored by McKinsey & Company. Teams of 4 will tackle real-world business challenges.",
                EventCategory.BUSINESS, "LUMS Entrepreneurship Society", "org_1",
                "SDSB Courtyard", "",
                "March 25, 2025", "11:00 AM", "5:00 PM",
                120, 120, 1000, false, EventStatus.LIVE
        ));

        events.add(new Event(
                "evt_5", "LUMS Literary Fest",
                "A celebration of literature featuring book readings, panel discussions with renowned Pakistani authors, and creative writing workshops.",
                EventCategory.ARTS, "Literary Society", "org_2",
                "Library Lawn", "",
                "April 1, 2025", "2:00 PM", "8:00 PM",
                300, 180, 0, false, EventStatus.LIVE
        ));

        events.add(new Event(
                "evt_6", "AI Workshop: LLMs in Practice",
                "Hands-on workshop covering practical applications of Large Language Models. Bring your laptop. Prerequisites: Basic Python knowledge.",
                EventCategory.TECH, "LUMS Computer Science Society", "org_3",
                "Online (Zoom)", "",
                "March 12, 2025", "3:00 PM", "5:00 PM",
                500, 234, 0, true, EventStatus.LIVE
        ));

        events.add(new Event(
                "evt_7", "Cricket Tournament Finals",
                "The grand finale of the inter-department cricket tournament. CS vs Business School. Come cheer for your department!",
                EventCategory.SPORTS, "LUMS Sports Society", "org_1",
                "LUMS Cricket Ground", "Behind Hostels",
                "March 18, 2025", "4:00 PM", "7:00 PM",
                500, 320, 0, false, EventStatus.LIVE
        ));

        events.add(new Event(
                "evt_8", "Guest Lecture: Future of Finance",
                "Distinguished lecture by Dr. Sarah Khan on the intersection of fintech and traditional banking in Pakistan's evolving economy.",
                EventCategory.ACADEMIC, "SDSB Dean's Office", "org_1",
                "SDSB Auditorium", "Ground Floor",
                "March 8, 2025", "11:00 AM", "1:00 PM",
                100, 78, 0, false, EventStatus.ENDED
        ));

        // RSVPs
        rsvps.add(new Rsvp("user_1", "Ahmed Raza", "ahmed.raza@lums.edu.pk", "evt_1", RsvpStatus.GOING));
        rsvps.add(new Rsvp("user_1", "Ahmed Raza", "ahmed.raza@lums.edu.pk", "evt_2", RsvpStatus.INTERESTED));
        rsvps.add(new Rsvp("user_1", "Ahmed Raza", "ahmed.raza@lums.edu.pk", "evt_3", RsvpStatus.GOING));
        rsvps.add(new Rsvp("user_2", "Sara Ali", "sara.ali@lums.edu.pk", "evt_1", RsvpStatus.GOING));
        rsvps.add(new Rsvp("user_2", "Sara Ali", "sara.ali@lums.edu.pk", "evt_3", RsvpStatus.GOING));
        rsvps.add(new Rsvp("user_3", "Omar Khan", "omar.khan@lums.edu.pk", "evt_1", RsvpStatus.INTERESTED));
        rsvps.add(new Rsvp("user_3", "Omar Khan", "omar.khan@lums.edu.pk", "evt_4", RsvpStatus.GOING));
        rsvps.add(new Rsvp("user_4", "Fatima Noor", "fatima.noor@lums.edu.pk", "evt_2", RsvpStatus.GOING));
        rsvps.add(new Rsvp("user_4", "Fatima Noor", "fatima.noor@lums.edu.pk", "evt_5", RsvpStatus.GOING));
        rsvps.add(new Rsvp("user_5", "Ali Hassan", "ali.hassan@lums.edu.pk", "evt_7", RsvpStatus.GOING));

        // Notifications
        notifications.add(new Notification(
                "notif_1", "evt_1", "LUMS MUN 2025",
                "Venue has been updated to LUMS Main Auditorium. Please check the event details.",
                "2 hours ago", NotificationType.UPDATE
        ));
        notifications.add(new Notification(
                "notif_2", "evt_3", "TechFest Spring 2025",
                "Reminder: TechFest starts tomorrow at 10:00 AM. Don't forget your laptop!",
                "5 hours ago", NotificationType.REMINDER
        ));
        notifications.add(new Notification(
                "notif_3", "evt_8", "Guest Lecture: Future of Finance",
                "This event has been cancelled due to unforeseen circumstances.",
                "1 day ago", NotificationType.CANCELLED
        ));

        // Event Updates
        eventUpdates.add(new EventUpdate(
                "upd_1", "evt_1",
                "The venue has been moved to LUMS Main Auditorium due to expected high turnout. All registered participants please take note.",
                "2 hours ago", UpdateType.VENUE_CHANGE
        ));
        eventUpdates.add(new EventUpdate(
                "upd_2", "evt_1",
                "Registration deadline extended to March 14. Hurry up and register!",
                "1 day ago", UpdateType.GENERAL
        ));
    }

    private MockData() {
        // Prevent instantiation
    }
}
