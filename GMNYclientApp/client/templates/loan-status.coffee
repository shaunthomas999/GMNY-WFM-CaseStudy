Template.loanStatus.helpers
  getLoanStatus: ->
  	loanStatusMsg = Session.get "loanStatusMessage"
  	return loanStatusMsg