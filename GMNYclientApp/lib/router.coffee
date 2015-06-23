# # router.coffee
# Contains all functionality related to push-ad algorithm
# - @copyright Arvato
# - @author Vanessa Ling & Shaun Thomas
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
  # - pushAds
  @route "pushAds",
    action: ->
      @render('pushAds', {to: 'content'})
      @render('appMain')
  # - appMain
  @route "appMain",
    path: "/home"
    yieldTemplates:
      homePage:
        to: "content"
    
    #- subscribe to product recommendations before the page is rendered but don't wait on the
    #- subscription, we'll just render the items as they arrive
    onBeforeAction: ->
      @productRecommendatiosHandle = Meteor.subscribe("productRecommendatios_PS")

    data: ->
      Products.find()

    action: ->
      @render()
  # - home
  @route "home",
    path: "/"
    action: ->
      Router.go "appMain", Products.find()
  # - orderList
  @route "orderList",
    action: ->
      @render('orderList', {to: 'content'})
      @render('appMain')
  # - shoppingCart
  @route "shoppingCart",
    action: ->
      @render('actionBar', {to: 'header'})
      @render('shoppingCart', {to: 'content'})
      @render('appMain')
  # - notifications
  @route "notifications"
  # - deliveryOptions
  @route "deliveryOptions",
    onBeforeAction: ->
      @locationsHandle = Meteor.subscribe("locations_PS")
      Meteor.subscribe "Orderheaders_PS"
      
    action: ->
      @render('actionBar', {to: 'header'})
      @render('deliveryOptions', {to: 'content'})
      @render('appMain')
  # - productDetail
  @route "productDetail",
    path: "/productdetail/:pid"
    waitOn: ->
      Meteor.subscribe("productDetail_PS", @params.pid)
      
    data: ->
      console.log "PID : " + @params.pid
      product = Products.find({"PID": parseInt(@params.pid)}).fetch()
      return product[0]

    action: ->
      @render('productDetail', {to: 'content'})
      @render('appMain')
  # - bundleList
  @route "bundleList",
    waitOn: ->
      Meteor.subscribe("demandforecasts_PS")
      Meteor.subscribe("bundles_PS")
      Meteor.subscribe("products_PS")

    action: ->
      @render('bundleList', {to: 'content'})
      @render('appMain')
  # - payment
  @route "payment",
    action: ->
      @render('actionBar', {to: 'header'})
      @render('payment', {to: 'content'})
      @render('appMain')