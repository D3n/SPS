# SupCrowdFunding

**[ J2EE | Servlets/JSP | JPA ]** 28/11/2013.

This is a SUPINFO BSc mini-project.
Achieved in 2 weeks.

# Requirements

* Tomcat `7`
* JDK `1.7.x`

# Setup

1. Import the SPS folder into Eclipse JAVA EE
* Create a DB MySQL named : `projectstarter`
* Modify `persistence.xml` with credentials and port
* Start the project with a Tomcat 7
* Default address is `http://localhost:8080/SPS/`
* First user created has Admin role
* Have fun and create : categories, users and projects to fund

# API

	GET /projects
	GET /projects/{id}
	GET /projects/byName/{name}
	POST /projects
	
	GET /categories
	GET /categories/{name}
	
	GET /users
	GET /contributions
	GET /groups 
	
# Project features

As anonymous:

* View current projects and information about them in the index page
* View projects ordered by categories on the categories page
* View projects’ information one by one thanks to a dedicated page
* Register as a new user, authenticate himself

As a registered user:

* Contribute on projects
* Create a project
* View and edit his profile
* Log out

As an administrator:

* Overall platform view with an easily usable dashboard
	* View all important data
	* Create, Read, Update, Delete items