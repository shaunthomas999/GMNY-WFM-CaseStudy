Session.setDefault "isCurrentCustomerPrivate", true
ERRORS_KEY = "joinErrors"
Template.join.created = ->
  Session.set ERRORS_KEY, {}
  return

Template.join.helpers
  errorMessages: ->
    _.values Session.get(ERRORS_KEY)

  errorClass: (key) ->
    Session.get(ERRORS_KEY)[key] and "error"

  isPrivateCustomer: ->
    if Session.get("isCurrentCustomerPrivate")
      return ""
    else
      return "display-none"

  isBusinessCustomer: ->
    if not Session.get("isCurrentCustomerPrivate")
      return ""
    else
      return "display-none"

Template.join.events
  "click #privCustomerOption":->
    Session.set "isCurrentCustomerPrivate", true

  "click #busCustomerOption":->
    Session.set "isCurrentCustomerPrivate", false

  "click #customerRegFormSubmit": (event) ->
    event.preventDefault()

    ###
    password = $('#password').val()
    confirmPassword = $('#confirmPassword').val()
    errors = {}
    errors.password = "Please enter password" unless password
    errors.confirmPassword = "Please confirm your password" unless confirmPassword
    errors.passwordMatch = "Password and confirm password should match" unless password is confirmPassword
    Session.set ERRORS_KEY, errors
    return  if _.keys(errors).length
    ###

    errors = {}
    customerObj = {}

    customerObj.firstName = $('#firstName').val()
    customerObj.lastName = $('#lastName').val()
    customerObj.email = $('#email').val()
    customerObj.streetNameNum = $('#street').val()
    customerObj.city = $('#city').val()
    customerObj.pincode = $('#pincode').val()
    customerObj.mobileNum = $('#contactNumber').val()

    if Session.get("isCurrentCustomerPrivate")
      customerObj.customerType = "private"
      customerObj.dateOfBirth = $('#dateOfBirth').val()
      customerObj.gender = $( "#gender option:selected" ).text();
      customerObj.orgName = ""
      customerObj.businessArea = ""
    else
      customerObj.customerType = "business"
      customerObj.dateOfBirth = ""
      customerObj.gender = ""
      customerObj.orgName = $('#orgName').val()
      customerObj.businessArea = $('#businessArea').val()
      
    password = $('#password').val()
    if not _.isEmpty password then customerObj.password = password else errors.password = "Please enter password"
    Session.set ERRORS_KEY, errors
    return if _.keys(errors).length

    confirmPassword = $('#confirmPassword').val()
    if confirmPassword then console.log "Password confirmed" else errors.confirmPassword = "Please confirm your password"
    Session.set ERRORS_KEY, errors
    return if _.keys(errors).length

    if password is confirmPassword then console.log "Password matched" else errors.passwordMatch = "Password and confirm password should match"
    Session.set ERRORS_KEY, errors
    return if _.keys(errors).length

    Meteor.call "customerRegistration", customerObj, (error, result) ->
      if error
        Router.go('errorPage')
      else
        Router.go('joinResponse')