package com.usermanagement

import groovy.json.JsonSlurper
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

import javax.management.relation.Role

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

    private static final long serialVersionUID = 1

    String name
    String pan
    String tan
    String gst
    String startDate
    String optionalFields
    String roles
    String address
    String bank
    String username
    String password

    boolean enabled = true
    boolean accountExpired = false
    boolean accountLocked = false
    boolean passwordExpired = false

    Set<Role> getAuthorities() {
        def parser = new JsonSlurper()
        def json = parser.parseText(roles)
        json as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
    }

    static mapping = {
        roles sqlType: "JSON"
        optionalFields sqlType: "JSON"
        address sqlType: "JSON"
        bank sqlType: "JSON"
	    password column: '`password`'
    }
}
