<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/view/stylesheet1.css">
</head>
    <body>
        <form action="<%=request.getContextPath()%>/sendMessage">
<div class="table">
           <div class="form-group">
            <div class="labelNm"><label for="name">Name: </label> </div>
            <div class="textNm"> <input type="text" id="name" name="name"/> </div>
            <div style="display:none"><input type="text" name="formParam" value="true"/></div>
	   </div>
	   <div class="radioButton">
            <div class="LabelSx"><label for="sex">Sex:</label></div>
	    <div class="radioSx"> <input type="radio" name="sex" value="F" checked> Female<br>
	          <input type="radio" name="sex" value="M"> Male  </div>
	    </div>
<div class="button">
            <button type="submit">Submit</button>
	   </div>   
</div>
	 
        <div class="tableSendResponse">
           ${tableSendResponse}
	   </div>
	   
	   
        </form>
    </body>
</html>