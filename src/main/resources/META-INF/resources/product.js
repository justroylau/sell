var app = new Vue({
  el: "#app",
  data: {
    query: '666',
    products: ''
  },
  methods: {
    search: function(){
      that =this;
      // axios.get("https://test.zhuihoude.com:1443/sell/buyer/product/list?openid="+this.query)
      axios.get("https://test.zhuihoude.com:1443/sell/buyer/product/list",{params: {t: Date().valueOf()}})
      .then(function(response){
        that.products = response.data.data;
      },function(err){})
    }
  }
  
})