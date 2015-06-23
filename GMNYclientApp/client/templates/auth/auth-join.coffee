ERRORS_KEY = "joinErrors"
Template.join.created = ->
  Session.set ERRORS_KEY, {}
  return

Template.join.helpers
  errorMessages: ->
    _.values Session.get(ERRORS_KEY)

  errorClass: (key) ->
    Session.get(ERRORS_KEY)[key] and "error"

Template.join.events
  "click #privCustomerOption":->
    $('#privateCustomerRegForm').show()
    $('#businessCustomerRegForm').hide()

  "click #busCustomerOption":->
    $('#privateCustomerRegForm').hide()
    $('#businessCustomerRegForm').show()

  "click #privateCustomerSubmit": (event) ->
    event.preventDefault()
    privateCustomerObj = {}
    privateCustomerObj.firstName = $('#priv_firstName').val()
    privateCustomerObj.lastName = $('#priv_lastName').val()
    privateCustomerObj.email = $('#priv_email').val()
    privateCustomerObj.streetNameNum = $('#priv_streetNameNum').val()
    privateCustomerObj.city = $('#priv_city').val()
    privateCustomerObj.pincode = $('#priv_pincode').val()
    privateCustomerObj.mobileNum = $('#priv_mobileNum').val()
    privateCustomerObj.DOB = $('#priv_DOB').val()

    Meteor.call "privateCustomerRegistration", privateCustomerObj, (error, result) ->
      if error
        Router.go('errorPage')
      else
        Router.go('joinResponse')

  "click #businessCustomerSubmit": (event) ->
    event.preventDefault()
    businessCustomerObj = {}
    businessCustomerObj.orgName = $('#bus_orgName').val()
    businessCustomerObj.streetNameNum = $('#bus_streetNameNum').val()
    businessCustomerObj.city = $('#bus_city').val()
    businessCustomerObj.pincode = $('#bus_pincode').val()
    businessCustomerObj.telNum = $('#bus_telNum').val()
    businessCustomerObj.YOF = $('#bus_YOF').val()
    businessCustomerObj.businessArea = $('#bus_businessArea').val()

    Meteor.call "businessCustomerRegistration", businessCustomerObj, (error, result) ->
      if error
        Router.go('errorPage')
      else
        Router.go('joinResponse')