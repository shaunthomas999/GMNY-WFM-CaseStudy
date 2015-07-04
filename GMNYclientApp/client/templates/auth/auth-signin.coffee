ERRORS_KEY = "signinErrors"
Template.signin.created = ->
    Session.set ERRORS_KEY, {}

Template.signin.helpers
    errorMessages: ->
        _.values Session.get(ERRORS_KEY)

    errorClass: (key) ->
        Session.get(ERRORS_KEY)[key] and "error"

Template.signin.events submit: (event, template) ->
    event.preventDefault()
    customerId = template.$("[name=customerId]").val()
    password = template.$("[name=password]").val()
    errors = {}
    errors.customerId = "Customer ID is required"  unless customerId
    errors.password = "Password is required"  unless password
    Session.set ERRORS_KEY, errors
    return  if _.keys(errors).length
    console.log "Authenticating user : " + customerId
    customerType = ""
    Meteor.call "authenticateUser", customerId, password, (error, result) ->
        if result is null
            return Session.set(ERRORS_KEY,
                none: "Authentication failed"
            )
        else
            customerObj = result
            Meteor.loginWithPassword customerId, password, (err) ->
                if err
                    return Session.set(ERRORS_KEY,
                        none: "Authentication failed"
                    )
                else
                    Session.set("currentuser", customerObj)
                    Router.go "home"