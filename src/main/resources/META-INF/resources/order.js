var app = new Vue({
  el: "#app",
  data: {
    query: '666',
    orders: ''
  },
  methods: {
    search: function(query){
      console.log("111");
      that =this;
      //axios.get("https://test.zhuihoude.com:1443/sell/buyer/order/list?openid="+this.query)
      axios.get("https://test.zhuihoude.com:1443/sell/buyer/order/list",{params: {openid: this.query,t: Date().valueOf()}})
      .then(function(response){
        console.log(response);
        that.orders = response.data.data;
        console.log(that.orders);
      },function(err){})
    }
  }
  
})