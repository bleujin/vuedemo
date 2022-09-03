<template>
  <q-page class="flex">

    <img alt="Quasar logo" src="../assets/logo.svg" style="width: 200px; height: 200px">


    <li>
      <button v-on:click="dbCall">Axios Test1</button>
    </li>

    <ul>
      <li v-for="(row, i) in items" :key="'result_table_' + i">{{ row }}</li>
    </ul>

  </q-page>
</template>

<style>
</style>

<script>

export default {
  name: 'HelloWorld', 
  methods :{
    dbCall: function(){
      // const userName = 'john"';
      // var procName =  `test@firstfn('${userName}', 3, true, array['abc','def'])` ;
      let params = {'proc' : "test@firstfn(?,?,?,?)", "args" : ["bleujin", 3, true, [12345678912345,123456789]] } ;
      console.log(JSON.stringify(params)) ;

      this.$axios.post('/api/query', JSON.stringify(params), {headers: {"Content-Type": 'application/json'}})
      .then(res => {
        this.items = res.data.node.rows ; 
        console.log(res.data.node.rows) ;
      })
    }
  }, 
  data(){
    return {
      items : []
    }
  }, 

  mounted(){
    console.log('created', this.$axios) ;
    this.rows = [] ;
  }, 
  computed(){
    console.log('computed')
  },
  created(){
    console.log('created') ;
  }
}

</script>
