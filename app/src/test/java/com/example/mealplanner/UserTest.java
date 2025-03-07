package com.example.mealplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.mealplanner.database.entities.User;

import org.junit.Test;

public class UserTest {

    @Test
    public void testEquals() {
        User user1 = new User("alice", "password");
        user1.setId(1);
        User user2 = new User("alice", "password");
        user2.setId(1);

        // Users with the same ID and attributes should be equal
        assertEquals(user1, user2);

        // Users with different IDs should not be equal
        user2.setId(2);
        assertNotEquals(user1, user2);

        // Users with different usernames should not be equal
        user2.setUsername("bob");
        assertNotEquals(user1, user2);

        // Users with different passwords should not be equal
        user2.setUsername("alice");
        user2.setPassword("newpassword");
        assertNotEquals(user1, user2);

        // Users with different isAdmin values should not be equal
        user2.setPassword("password");
        user2.setAdmin(true);
        assertNotEquals(user1, user2);
    }

    @Test
    public void testHashCode() {
        User user1 = new User("alice", "password");
        user1.setId(1);
        User user2 = new User("alice", "password");
        user2.setId(1);

        // Hash code should be equal for users with the same attributes
        assertEquals(user1.hashCode(), user2.hashCode());

        // Changing any attribute should change the hash code
        user2.setId(2);
        assertNotEquals(user1.hashCode(), user2.hashCode());

        user2.setId(1);
        user2.setUsername("bob");
        assertNotEquals(user1.hashCode(), user2.hashCode());

        user2.setUsername("alice");
        user2.setPassword("newpassword");
        assertNotEquals(user1.hashCode(), user2.hashCode());

        user2.setPassword("password");
        user2.setAdmin(true);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}
