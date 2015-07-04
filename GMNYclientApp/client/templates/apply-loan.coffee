Template.applyLoan.helpers
  isPrivateCustomer: ->
    customerObj = Session.get("currentuser")
    if customerObj.customerType is "private" then return true else return false

  privateCustomerCssClass: ->
    customerObj = Session.get("currentuser")
    if customerObj.customerType is "private"
      return ""
    else
      return "display-none"

  businessCustomerCssClass: ->
    customerObj = Session.get("currentuser")
    if customerObj.customerType is "business"
      return ""
    else
      return "display-none"

Template.applyLoan.events

  "click #loanApplicationFormSubmit": (event) ->
    event.preventDefault()
    loanObj = {}

    loanObj.amount = $('#amount').val()
    loanObj.period = $('#period').val()

    customerObj = Session.get("currentuser")
    loanObj.customerId = customerObj.id
    if customerObj.customerType is "private"
      loanObj.occupation = $('#occupation').val()
      loanObj.annualSalary = $('#annualSalary').val()
      loanObj.loanType = $( "#loanType option:selected" ).text()
    else
      loanObj.occupation = ""
      loanObj.annualSalary = ""
      loanObj.loanType = $( "#loanType option:selected" ).text()

    Meteor.call "loanApplication", loanObj, (error, result) ->
      if error
        Router.go('errorPage')
      else
        Router.go('applyLoanResponse')