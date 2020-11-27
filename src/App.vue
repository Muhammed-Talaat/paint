<template>
  <div id="app">
    <navBar/>
  </div>
</template>

<script>
import navBar from './components/navBar'
export default {
  name: 'app',
  components: {
    navBar
  },
  data:function(){return{zz:20,m:false}},
  computed:{
     n:function(){
       return{'nc': this.zz>25}
     }
  }
  
}

</script>
<style>
.nc{
  background-color: rebeccapurple;
}
</style>