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
        <div class="box-compon" id="line" @click="tool('line')">
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
        <div class="box-compon" id="copy" @click="tool('copy')">
          <img src="https://img.icons8.com/dusk/64/000000/copy.png" style="height:68%;position: relative;margin:13%;"/>
        </div>
        <div class="box-compon" id="resize" @click="tool('resize')">
          <img src="https://img.icons8.com/nolan/64/resize.png" style="height:68%;position: relative;margin:13%;"/>
        </div>
        <div class="box-compon" id="delete" @click="tool('delete')">
          <img src="https://img.icons8.com/dusk/64/000000/delete-forever.png" style="height:68%;position: relative;margin:13%;"/>
        </div>
        <div class="box-compon" id="fill" @click="tool('fill')">
          <img src="https://img.icons8.com/dusk/64/000000/fill-color.png" style="height:68%;position: relative;margin:13%;"/>
        </div>
        <input id="input" type="color" value="#ffffff"/>
        <canvas id="my-canvas" width="1290" height="612" ></canvas>
    </div>
    </div>
</template>
<script>

export default {
   name: 'app',
   data:function(){return{current:'',canvas:'',
   //mouse location
   mouseup:{x:0,y:0},mousedown:{x:0,y:0},
   //current location of the mouse
   loc:{x:0,y:0},
   ///////////////////////////////
   ctx:'',savedImageData:'',
   dragging:false,
   //Arrays to held App's shapes
   shapes:[],shapedRedo:[],
   fillColor:'#ffffff',borderColor:'#000000',
   linwidth:2,canvasWidth:1290,canvasHeight:612,
   bounds:{
     x:0,y:0,width:0,height:0
   },
   }},
   methods: {
    tool: function(selectedTool) {
      //highlighted the selected button
      document.getElementById("save").className = "box-compon";
      document.getElementById("load").className = "box-compon";
      document.getElementById("undo").className = "box-compon";
      document.getElementById("redo").className = "box-compon";
      document.getElementById("line").className = "box-compon";
      document.getElementById("triangle").className = "box-compon";
      document.getElementById("rectangle").className = "box-compon";
      document.getElementById("circle").className = "box-compon";
      document.getElementById("ellipse").className = "box-compon";
      document.getElementById("copy").className = "box-compon";
      document.getElementById("resize").className = "box-compon";
      document.getElementById("delete").className = "box-compon";
      document.getElementById("fill").className = "box-compon";
      document.getElementById(selectedTool).className = "box-compon-selected";
      this.current = selectedTool;
      if(selectedTool==='undo'){
      //backend stuff
      this.undo();
      }
      else if(selectedTool==='redo'){
      //backend stuff
      this.redo();
      }
    },
    undo:function(){
        /////////////////////////////////////////////////////////////
        //backend stuff
        if(this.shapes.length>0){
        this.shapedRedo.push(this.shapes.pop())}
        this.displayShapes();
    },
    redo:function(){
        /////////////////////////////////////////////////////////////
        //backend stuff
        if(this.shapedRedo.length>0){
        this.shapes.push(this.shapedRedo.pop())}
        this.displayShapes();
    },
    //get position relative to cavan's panel
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
      //this.fillColor="#ffffff";
      if(this.shapes.length>0){
        //adjust draging
        this.dragging=!(this.current==='fill'||this.current==='delete'||
                         this.current==='copy'||this.current==='resize')
        if(this.current==='fill'){
        //backend stuff
        //let shape_=getShapeBack(this.mousedown)
        //this.setColor(shape_,this.fillColor);
        //this.displayShapes();
      }
        else if(this.current==='delete'){
        //backend stuff
        //let shape_=getShapeBack(this.mousedown)
        //this.deleteShape(shape_);
        //this.displayShapes();
      }
        else if(this.current==='resize'){
        //backend stuff
        //let shape_=getShapeBack(this.mousedown)
        //somestuff
        //let shape_2=//somestuff
        //this.resizeShape(shape_2);
        //this.displayShapes();
        //this.resizeBack();
      }
      
      }
    },
      //get the shape corresponding to mousedown click 
      //backend stuff
      //////getShapeBack:function(_location){
      //////},
      //set color for a shape
      setColor:function(shape_,color_){
      //check if there was a shape from backend
      if(shape_!=null){
      let ID =shape_.id;
      for(let i=0;i<this.shapes.length;i++){
        if(ID===this.shapes[i].id){
          this.shapes[i]._color=color_;
          break;
        }
      }
      return null;}
      },
      //delete a returned shape if found
      deleteShape:function(shape_){
      if(shape_!=null){
      let ID =shape_.id;
      for(let i=0;i<this.shapes.length;i++){
        if(ID===this.shapes[i].id){
          this.shapes.splice(i,1);
          break;
        }
      }}
      },
      resizeShape:function(shape_){
      if(shape_!=null){
      let ID =shape_.id;
      for(let i=0;i<this.shapes.length;i++){
        if(ID===this.shapes[i].id){
          this.shapes[i]=shape_;
          break;
        }
      }}
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
      //get an array on shapes and display them
      this.ctx.clearRect(0, 0, this.canvasWidth, this.canvasHeight);
      ////////////////////////////////////////////////////////////////
      for(let i=0;i<this.shapes.length;i++){
      this.colorShape(this.shapes[i]);
      let typ=this.shapes[i].type;
      let dims=this.shapes[i].dim;
      let _mouseup=this.shapes[i].mouse_up;
      let _mousedown=this.shapes[i].mouse_down;
      if(typ==='line'){
        this.ctx.beginPath();
        this.ctx.moveTo(_mousedown.x,_mousedown.y);
        this.ctx.lineTo(_mouseup.x,_mouseup.y);
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
        this.ctx.arc(_mousedown.x,_mousedown.y,radius,0,Math.PI *2);
        this.ctx.stroke();
      }

      else if(typ==='ellipse'){
        let radiusX = dims.width;
        let radiusY = dims.height;
        this.ctx.beginPath();
        this.ctx.ellipse(_mousedown.x,_mousedown.y, radiusX, radiusY, 0, 0, Math.PI * 2);
        this.ctx.stroke();
      }
      //update
      this.SaveCanvasImage();
      this.RedrawCanvasImage();
      }
      },
    setMouseUp: function(event){
      this.canvas.style.cursor="default";
      this.loc = this.getPosition(event.clientX,event.clientY);
      this.SaveCanvasImage();
      this.RedrawCanvasImage();
      this.updatebounds(this.loc);
      this.mouseup=this.loc;
      ////////////////////////////////////////////////////////
      this.dragging=false;
      //insert shapes and sent them to backend
      this.insertShape();
      //update color
      ////////////////////////////////////////////////////////
      //////////////send to backend///////////////
      //let _shape=this.getShape();
      //this.__sendBack(_shape);
    },
      //get a shape object from current data
      //shape on format of {dim:x,y upperleft,width,height of the bounding box,type,id,color,mouseup,mousedown}
      getShape:function(){
        let dimCloned=Object.assign({},this.bounds);
        let _mouseup=Object.assign({},this.mouseup);
        let _mousedown=Object.assign({},this.mousedown);
        let _id=this.getID();
        let _type=this.current;
        //start with white as a default color
        return {dim:dimCloned,type:_type,id:_id,
                    _color:'#ffffff',mouse_up:_mouseup,mouse_down:_mousedown}
       },
      insertShape:function(){
      if((this.mouseup.x!=this.mousedown.x || this.mouseup.y!=this.mousedown.y)){
        //id-color
        this.shapes.push(this.getShape())
      }
    },
    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    __sendBack:function(/*_shape*/){
    ////////////backend
    },
    getID:function(){
      //start with random number
      let ID=Math.floor(Math.random()*1000000);
      //check all ID's
      this.shapes.forEach(elem => {
        while(elem.id===ID){
        ID=Math.floor(Math.random()*1000000);}
      })
      return ID;
    },
    updateboundsActive: function(locati){
       //live update while moving
       this.updatebounds(locati);
       this.drawShape();
    },
    drawShape: function(){
      this.ctx.strokeStyle=this.borderColor;
      this.ctx.lineWidth = this.linwidth;
        ////////////////////////////////////////////
      if(this.current==='rectangle'){
      this.ctx.strokeRect(this.bounds.x,this.bounds.y,this.bounds.width,this.bounds.height);
      //color in 129////////////
      }
      else if(this.current==='line'){
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
        this.ctx.stroke();//////////////
      }
      else if(this.current === "ellipse"){
        let radiusX = this.bounds.width;
        let radiusY = this.bounds.height;
        this.ctx.beginPath();

        this.ctx.ellipse(this.mousedown.x,this.mousedown.y,
                      radiusX, radiusY, 0, 0, Math.PI * 2);
        this.ctx.stroke();//////////////
      } 

      else if(this.current === "triangle"){
        let polypoints = [{x:this.bounds.x+(this.bounds.width/2),y:this.bounds.y},
                             {x:this.bounds.x,y:this.bounds.y+this.bounds.height},
          {x:this.bounds.x+this.bounds.width,y:this.bounds.y+this.bounds.height}];
        this.ctx.beginPath();
        this.ctx.moveTo(polypoints[0].x, polypoints[0].y);
       //attach all the points of the triangle 
       for(let i = 1; i < 3; i++){
        this.ctx.lineTo(polypoints[i].x, polypoints[i].y);
       }
        this.ctx.closePath();
        this.ctx.stroke();
      }
      },
      //implement the property inside a shape
      colorShape:function(_shape){

      //set color for fill
      //check shape dimensions
      if(_shape._color!='#ffffff'){
      this.ctx.fillStyle=_shape._color;
        if(_shape.type==='rectangle'){
        this.ctx.fillRect(_shape.dim.x,_shape.dim.y,
                _shape.dim.width,_shape.dim.height);
        }
        else if(_shape.type==='circle'){
        let radius=_shape.dim.width;
        this.ctx.beginPath();
        this.ctx.arc(_shape.mouse_down.x,_shape.mouse_down.y,
        radius,0,Math.PI *2);
             this.ctx.fill();
        }
        else if(_shape.type==='ellipse'){
        let radiusX = _shape.dim.width;
        let radiusY = _shape.dim.height;
        this.ctx.beginPath();
        this.ctx.ellipse(_shape.mouse_down.x,_shape.mouse_down.y,radiusX,
        radiusY,0, 0, Math.PI * 2);
                   this.ctx.fill(); 
        }
        else if(_shape.type==='triangle'){
        let polypoints = [{x:_shape.dim.x+_shape.dim.width/2,y:_shape.dim.y},
                           {x:_shape.dim.x,y:_shape.dim.y+_shape.dim.height},
         {x:_shape.dim.x+_shape.dim.width,y:_shape.dim.y+_shape.dim.height}];
        this.ctx.beginPath();
        this.ctx.moveTo(polypoints[0].x, polypoints[0].y);
       //attach all the points of the triangle 
        for(let i = 1; i < 3; i++){
        this.ctx.lineTo(polypoints[i].x, polypoints[i].y);
        }
        this.ctx.closePath();
             this.ctx.fill();
    }}
    },
    SaveCanvasImage:function(){
    //Save image
    this.savedImageData = this.ctx.getImageData(0,0,this.canvasWidth,this.canvasHeight);
    },

    RedrawCanvasImage:function(){
    //Restore image
    this.ctx.putImageData(this.savedImageData,0,0);},
    ////////////////////////////////
    setupCanvas: function() {
      //set all the stuff
      this.canvas = document.getElementById('my-canvas');
      this.ctx = this.canvas.getContext('2d');
      this.ctx.strokeStyle = this.borderColor;
      this.ctx.lineWidth = this.linwidth;
      this.canvas.addEventListener("mousedown", this.setMouseDown);
      this.canvas.addEventListener("mousemove", this.setMouseMove);
      this.canvas.addEventListener("mouseup", this.setMouseUp);
    }
},

mounted(){
  document.addEventListener('DOMContentLoaded',this.setupCanvas);
  this.fillColor=document.getElementById('input').value;
}
}


</script>
<style scoped>



.box{
    height:80px;
    width: 1300px;
    background-image: linear-gradient(rgb(70, 0, 72), rgb(52, 0, 51), rgb(76, 0, 73));
    border-radius: 15px 30px 0px 0px;
    box-shadow: 6px 6px 6px rgb(177, 0, 153) inset;
    margin: auto;
}


.box-compon,.box-compon-selected{
    position: relative;
    float: left;
    width: 6.8%;
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
    width: 2.6%;
    height: 46%;
    margin: 1.56% 1.36%;
    text-align: center;
    padding: 0px 0px;
}

#my-canvas{
    border: 5px solid  rgb(52, 0, 51) ;
    border-radius: 0px 0px 10px 20px;
}

</style>



