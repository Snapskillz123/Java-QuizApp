package com.quiz.service;

/**
 * Service class for handling authentication operations
 * This class manages admin password verification and security
 */
public class AuthenticationService {
    private static AuthenticationService instance;
    private final String ADMIN_PASSWORD = "admin123"; // In a real app, this should be encrypted and stored securely
    
    /**
     * Private constructor for singleton pattern
     */
    private AuthenticationService() {
    }
    
    /**
     * Gets the singleton instance of AuthenticationService
     * @return The AuthenticationService instance
     */
    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }
    
    /**
     * Verifies the admin password
     * @param password The password to verify
     * @return true if password is correct, false otherwise
     */
    public boolean verifyAdminPassword(String password) {
        return password != null && password.equals(ADMIN_PASSWORD);
    }
    
    /**
     * Gets the admin password (for internal use only)
     * Note: In a real application, passwords should never be retrievable
     * @return The admin password
     */
    protected String getAdminPassword() {
        return ADMIN_PASSWORD;
    }
    
    /**
     * Changes the admin password
     * Note: In a real application, this would involve proper encryption and storage
     * @param currentPassword The current password
     * @param newPassword The new password
     * @return true if password was changed successfully
     */
    public boolean changeAdminPassword(String currentPassword, String newPassword) {
        // This is a placeholder for password change functionality
        // In a real app, you would:
        // 1. Verify current password
        // 2. Encrypt new password
        // 3. Store in secure location
        // 4. Return success/failure
        return verifyAdminPassword(currentPassword) && newPassword != null && !newPassword.trim().isEmpty();
    }
}
