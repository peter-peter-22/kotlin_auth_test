//package com.example.backend.repository
//
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.provisioning.UserDetailsManager
//import org.springframework.stereotype.Service
//
//@Service
//class DatabaseUserDetailsManager: UserDetailsManager {
//    override fun createUser(user: UserDetails?){
//        println(user)
//    }
//
//    /**
//     * Update the specified user.
//     */
//    open fun updateUser(user: UserDetails?)
//
//    /**
//     * Remove the user with the given login name from the system.
//     */
//    open fun deleteUser(username: String?)
//
//    /**
//     * Modify the current user's password. This should change the user's password in the
//     * persistent user repository (database, LDAP etc).
//     * @param oldPassword current password (for re-authentication if required)
//     * @param newPassword the password to change to
//     */
//    open fun changePassword(oldPassword: String?, newPassword: String?)
//
//    /**
//     * Check if a user with the supplied login name exists in the system.
//     */
//    open fun userExists(username: String?): Boolean
//
//}