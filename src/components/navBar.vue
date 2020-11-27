<template>
    <div id="app">
    <div class="box">
        <div class="box-compon" id="save" @click="tool('save')">
          <img src="https://img.icons8.com/ultraviolet/40/000000/save-as.png" style="height:58%;position: relative;margin:13%;"/></div>
        <div class="box-compon" id="load" @click="tool('load')">
         <img src="https://img.icons8.com/nolan/64/load-cargo.png" style="height:58%;position: relative;margin:13%;"/></div>
        <div class="box-compon" id="undo" @click="tool('undo')">
          <img src="https://img.icons8.com/nolan/64/undo.png" style="height:58%;position: relative;margin:13%;"/>
        </div>
        <div class="box-compon" id="redo" @click="tool('redo')">
          <img src="https://img.icons8.com/nolan/64/redo.png" style="height:58%;position: relative;margin:13%;"/>
        </div>
        <div class="box-compon" id="segLine" @click="tool('segLine')">
          <img src="https://img.icons8.com/dusk/64/000000/line.png" style="height:58%;position: relative;margin:13%;"/>
        </div>
        <div class="box-compon" id="triangle" @click="tool('triangle')">
          <img src="https://img.icons8.com/fluent/48/000000/triangle-stroked.png" style="height:58%;position: relative;margin:13%;"/>
        </div>
        <div class="box-compon" id="rectangle" @click="tool('rectangle')">
          <img src="https://img.icons8.com/nolan/64/rectangle.png" style="height:68%;position: relative;margin:13%;"/>
        </div>
        <div class="box-compon" id="circle" @click="tool('circle')">
          <img src="https://img.icons8.com/nolan/64/filled-circle.png" style="height:68%;position: relative;margin:13%;"/>
        </div>
        <div class="box-compon" id="ellipse" @click="tool('ellipse')">
          <img src="https://img.icons8.com/color/48/000000/ellipse.png" style="height:68%;position: relative;margin:13%;"/>
        </div>
        <input type="color"/>
        <canvas id="my-canvas" width="990" height="612" margin="auto"></canvas>
    </div>
    </div>
</template>
<script>

export default {
   name: 'app',
   data:function(){return{current:'',canvas:'',
   mouseup:{x:0,y:0},ctx:'',savedImageData:'',
   dragging:false,shapes:[],shapedRedo:[],
   mousedown:{x:0,y:0},
   fillColor:'white',borderColor:'black',
   loc:{x:0,y:0},
   linwidth:2,canvasWidth:990,canvasHeight:612,scc:'',
   bounds:{
     x:0,y:0,width:0,height:0
   },
    mouseDownTrack:{
      constructor:function(x,y){
        this.x=x;
        this.y=y;
        return this;
      }
    },
    mouseLocation:{
      constructor:function(x,y){
        this.x=x;
        this.y=y;
        return this;
      }
    },

   }},
   methods: {
    tool: function(selectedTool) {
      document.getElementById("save").className = "box-compon";
      document.getElementById("load").className = "box-compon";
      document.getElementById("undo").className = "box-compon";
      document.getElementById("redo").className = "box-compon";
      document.getElementById("segLine").className = "box-compon";
      document.getElementById("triangle").className = "box-compon";
      document.getElementById("rectangle").className = "box-compon";
      document.getElementById("circle").className = "box-compon";
      document.getElementById("ellipse").className = "box-compon";
      document.getElementById(selectedTool).className = "box-compon-selected";
      this.current = selectedTool;
      if(selectedTool==='undo'){
      this.undo();
      }
      else if(selectedTool==='redo'){
      this.redo();
      }
    },
    undo:function(){
        if(this.shapes.length>0){
        this.shapedRedo.push(this.shapes.pop())}
        this.displayShapes();
    },
    redo:function(){
        if(this.shapedRedo.length>0){
        this.shapes.push(this.shapedRedo.pop())}
        this.displayShapes();
    },
    setupCanvas: function() {
      this.canvas = document.getElementById('my-canvas');
      this.ctx = this.canvas.getContext('2d');
      this.ctx.strokeStyle = this.borderColor;
      this.ctx.lineWidth = this.linwidth;
      this.canvas.addEventListener("mousedown", this.setMouseDown);
      this.canvas.addEventListener("mousemove", this.setMouseMove);
      this.canvas.addEventListener("mouseup", this.setMouseUp);
    },
    
    getPosition: function(x,y){
      let cav= this.canvas.getBoundingClientRect(); 
      return {x:(x-cav.left),y:(y-cav.top)}
    },
    //////////////////////////////////////////////////////////
    updatebounds:function(pointt){
      this.bounds.width=Math.abs(pointt.x-this.mousedown.x)
      this.bounds.height=Math.abs(pointt.y-this.mousedown.y)
      this.bounds.x=(pointt.x>this.mousedown.x)?
      this.mousedown.x:pointt.x;
      this.bounds.y=(pointt.y>this.mousedown.y)?
      this.mousedown.y:pointt.y;
    },
    setMouseDown:function(event){
      this.canvas.style.cursor="crosshair"; 
      this.loc = this.getPosition(event.clientX,event.clientY);
      //save
      this.SaveCanvasImage();
      this.mousedown.x=this.loc.x;
      this.mousedown.y=this.loc.y;
      this.dragging=true;
    },
    setMouseMove: function(event){
      this.canvas.style.cursor="crosshair";
      this.loc = this.getPosition(event.clientX,event.clientY);
      if(this.dragging){
        //////
        this.RedrawCanvasImage();
        this.updateboundsActive(this.loc);
      }
    },displayShapes:function(){
      this.ctx.clearRect(0, 0, this.canvasWidth, this.canvasHeight);
      for(let i=0;i<this.shapes.length;i++){
      let typ=this.shapes[i].type;
      let dims=this.shapes[i].dim;
      if(typ==='line'){
        this.ctx.beginPath();
        this.ctx.moveTo(dims.x,dims.y);
        this.ctx.lineTo((dims.x+dims.width),(dims.y+dims.height));
        this.ctx.stroke();
      }
      else if(typ==='triangle'){
        let polypoints = [{x:dims.x+(dims.width/2),y:dims.y},
        {x:dims.x,y:dims.y+dims.height},
        {x:dims.x+dims.width,y:dims.y+dims.height}];
        this.ctx.beginPath();
        this.ctx.moveTo(polypoints[0].x, polypoints[0].y);
       for(let i = 1; i < 3; i++){
        this.ctx.lineTo(polypoints[i].x, polypoints[i].y);
        }
        this.ctx.closePath();
        this.ctx.stroke();
       }
      else if(typ==='rectangle'){
      this.ctx.strokeRect(dims.x,dims.y,dims.width,dims.height);
      }

      else if(typ==='circle'){
        let radius=dims.width;
        this.ctx.beginPath();
        this.ctx.arc(dims.x,dims.y,radius,0,Math.PI *2);
        this.ctx.stroke();
      }

      else if(typ==='ellipse'){
        let radiusX = dims.width;
        let radiusY = dims.height;
        this.ctx.beginPath();
        this.ctx.ellipse(dims.x,dims.y, radiusX, radiusY, 0, 0, Math.PI * 2);
        this.ctx.stroke();
      }
      this.SaveCanvasImage();
      this.RedrawCanvasImage();
      }
      },
      insertShape:function(){
      if((this.mouseup.x!=this.mousedown.x || this.mouseup.y!=this.mousedown.y)){
        //id-color 
        let dimCloned=Object.assign({},this.bounds)
      if(this.current==='segLine'){
        this.shapes.push({dim:dimCloned,type:'line'});
      }
      else if(this.current==='triangle'){
        this.shapes.push({dim:dimCloned,type:'triangle'});
      }
      else if(this.current==='rectangle'){
        this.shapes.push({dim:dimCloned,type:'rectangle'});
      }
      else if(this.current==='circle'){
        this.shapes.push({dim:dimCloned,type:'circle'});
      }
      else if(this.current==='ellipse'){
        this.shapes.push({dim:dimCloned,type:'ellipse'});
      }
      }
    },
    setMouseUp: function(event){
      this.canvas.style.cursor="default";
      this.loc = this.getPosition(event.clientX,event.clientY);
      this.SaveCanvasImage();
      this.RedrawCanvasImage();
      this.updatebounds(this.loc);
      ////////////////////////////////////////////////////////
      this.dragging=false;
      this.insertShape();
      /////////////////
    },
    updateboundsActive: function(locati){
       this.updatebounds(locati);
       this.drawShape();
    },
    drawShape: function(){
      this.ctx.strokeStyle=this.borderColor;
      this.ctx.fillStyle=this.fillColor;
      this.ctx.lineWidth = this.linwidth;
      if(this.current==='rectangle'){
      this.ctx.strokeRect(this.bounds.x,this.bounds.y,this.bounds.width,this.bounds.height);}
      else if(this.current==='segLine'){
        //Draw Line
        this.ctx.beginPath();
        this.ctx.moveTo(this.mousedown.x,this.mousedown.y);
        this.ctx.lineTo(this.loc.x, this.loc.y);
        this.ctx.stroke();

      }

      else if(this.current==='circle'){
        let radius=this.bounds.width;
        this.ctx.beginPath();
        this.ctx.arc(this.mousedown.x,this.mousedown.y,
                                      radius,0,Math.PI *2);
        this.ctx.stroke();
      }
      else if(this.current === "ellipse"){
        let radiusX = this.bounds.width;
        let radiusY = this.bounds.height;
        this.ctx.beginPath();

        this.ctx.ellipse(this.mousedown.x,this.mousedown.y,
                        radiusX, radiusY, 0, 0, Math.PI * 2);
        this.ctx.stroke();
      } 

      else if(this.current === "triangle"){
        let polypoints = [{x:this.bounds.x+(this.bounds.width/2),y:this.bounds.y},
        {x:this.bounds.x,y:this.bounds.y+this.bounds.height},
        {x:this.bounds.x+this.bounds.width,y:this.bounds.y+this.bounds.height}];
        this.ctx.beginPath();
        this.ctx.moveTo(polypoints[0].x, polypoints[0].y);

       for(let i = 1; i < 3; i++){
        this.ctx.lineTo(polypoints[i].x, polypoints[i].y);
       }
        this.ctx.closePath();
        this.ctx.stroke();
      }
    },
    SaveCanvasImage:function(){
    //Save image
    this.savedImageData = this.ctx.getImageData(0,0,this.canvasWidth,this.canvasHeight);
    },

    RedrawCanvasImage:function(){
    //Restore image
    this.ctx.putImageData(this.savedImageData,0,0);}
    ////////////////////////////////
},
mounted(){
  this.mouseDownTrack=this.mouseDownTrack.constructor(0,0)
  this.mouseLocation=this.mouseLocation.constructor(0,0)
  document.addEventListener('DOMContentLoaded',this.setupCanvas)
}
}


</script>
<style scoped>



.box{
    height:80px;
    width: 1000px;
    background-image: linear-gradient(rgb(70, 0, 72), rgb(52, 0, 51), rgb(76, 0, 73));
    border-radius: 15px 30px 0px 0px;
    box-shadow: 6px 6px 6px rgb(177, 0, 153) inset;
    margin: auto;
}


.box-compon,.box-compon-selected{
    position: relative;
    float: left;
    width: 9.79%;
    height: 91%;
    margin: 3px 3px;
    text-align: center;
    padding: 0px 0px;
    box-shadow: 3px 3px 3px rgb(133, 0, 127) inset;
    cursor: pointer;
    outline:none;
    border-radius: 15px 30px;
}


.box-compon{
  background-color: rgb(85, 0, 99);
}

.box-compon:hover{
 background-color:rgb(114, 0, 155);
 transition: all 0.36s ease;
}

.box-compon-selected {
    background-color:rgb(114, 0, 155);
}

input[type=color]{
    position: relative;
    float: right;
    width: 3.38%;
    height: 46%;
    margin: 2% 1.36%;
    text-align: center;
    padding: 0px 0px;
}

#my-canvas{
    border: 5px solid  rgb(52, 0, 51) ;
    border-radius: 0px 0px 10px 20px;
}
</style>



















