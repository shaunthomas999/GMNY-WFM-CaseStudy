# # router.coffee
root = global ? window

# ## Router.configure
Router.configure
  # - layoutTemplate: "appBody"
  # we use the  appBody template to define the layout for the entire app
  layoutTemplate: "appBody"
  
  # - notFoundTemplate: "appNotFound"
  # the appNotFound template is used for unknown routes and missing lists
  notFoundTemplate: "appNotFound"
  
  # - loadingTemplate: "appLoading"
  # show the appLoading template whilst the subscriptions below load their data
  loadingTemplate: "appLoading"

# ## Router.map
# Different routes available
Router.map ->
  # - join
  @route "join"
  # - signin
  @route "signin"
  # - appMain
  @route "appMain",
    path: "/home"
    yieldTemplates:
      homePage:
        to: "content"

    action: ->
      @render()
  # - home
  @route "home",
    path: "/"
    action: ->
      Router.go "appMain"
  # - error-page
  @route "errorPage",
    action: ->
      @render('errorPage', {to: 'content'})
      @render('appMain')
  # - join-response
  @route "joinResponse",
    action: ->
      @render('joinResponse', {to: 'content'})
      @render('appMain')
  # - apply loan
  @route "applyLoan",
    action: ->
      @render('applyLoan', {to: 'content'})
      @render('appMain')
  # - apply-loan-response
  @route "applyLoanResponse",
    action: ->
      @render('applyLoanResponse', {to: 'content'})
      @render('appMain')
  # - loan-status
  @route "loanStatus",
    action: ->
      @render('loanStatus', {to: 'content'})
      @render('appMain')