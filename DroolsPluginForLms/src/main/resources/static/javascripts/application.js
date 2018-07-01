
function addData(){	
	
	var scope = $("#addfileModal");	
	var id =  scope.find('#id').val();	
	if(id!=""){
		updateDroolsFile();
		return;
	}	
    var formData = new FormData();
	var scope = $("#addfileModal");	
	var title =  scope.find('#title').val();
    var totalFiles = document.getElementById("file").files.length;
        formData.append("title", title);
	   for (var i = 0; i < totalFiles; i++){
	       var file = document.getElementById("file").files[i];
	       formData.append("file", file);
	   }	
    $.ajax({
        url: '/save',
        type: 'POST',
        data: formData,
        cache : false,
        contentType : false,
        processData : false,
        success: function (data) {
        	listData();
        	$('#addfileModal').modal('hide'); 
            alert(data.message);
            cleanForm();
        },
        error: function (err) {
            alert(JSON.stringify(err));
        }
    });
}

function listData(){
	var refRowIndex=1;
    var i = 1;	
	$.ajax({
		 url:'list',
	 type:'GET',
	 success: function(data){
		 $("#droolsFileListTable_tBody tr").remove();
	   if(data != null){
         $.each(data, function(index, val) {
            var newRow = '<tr>' +
                '<td><label id="id'+ refRowIndex + '"style="display: none">' + val.id + '</label>' +
                '<label id="title' +refRowIndex + '">' + val.title  + '</label></td>' +
                '<td><label id="drools_file' +refRowIndex + '">' + val.drools_file + '</label></td>' +          
                '<td><a href="#" onclick="editDroolsFile(this,' +refRowIndex + ')">Edit</a> |'+
                '<a href="#" onclick="deleteDroolsFile(this,' +refRowIndex + ')">Delete</a> |' +          
                '<a href="#" onclick="activatedRules(this,' +refRowIndex + ')">Active</a></td>' +          
                '</tr>';      	
                $ ("#droolsFileListTable_tBody").append(newRow); 
                    refRowIndex++;                   
                });	
	         }			   
			 console.log(JSON.stringify(data))
		 },
		 error: function (err) {
	            alert(JSON.stringify(err));
	     }
		 
	 });
		
}

function activatedRules(i, refRowIndex){
	var scope = $("#droolsFileListTable_tBody");
	var id= scope.find('#id'+refRowIndex).text();
		$.ajax({
	          type: 'GET',
	          url: "/activatedRules/"+id,
		          success: function (data) {
		          	alert(data);  
		          },
		          error: function (err) {
		              alert(JSON.stringify(err));
		          }
		      });
}

function editDroolsFile(i, refRowIndex){
	var scope = $("#droolsFileListTable_tBody");
    var id= scope.find('#id'+refRowIndex).text();  
    $.ajax({ 
        type: 'GET',
        url: "/edit/"+id,
	          success: function (data) {
	        	console.log(JSON.stringify(data));
	        	var scope = $("#addfileModal");	
	        	scope.find('#title').val(data.title);
	        	scope.find('#currentFile').val(data.drools_file);
	        	scope.find('#id').val(data.id);
	        	$('#addfileModal').modal('show'); 
	        	
	          },
	          error: function (err) {
	              alert(JSON.stringify(err));
	          }
	      }); 
}

function updateDroolsFile(){
	var formData = new FormData();
    var scope = $("#addfileModal");	
	var id =  scope.find('#id').val();
	var title =  scope.find('#title').val();
	var currentFile =  scope.find('#currentFile').val();
	
	    formData.append("id", id);
	    formData.append("title", title);
	    formData.append("currentFile", currentFile);
	
    var totalFiles = document.getElementById("file").files.length;
	   for (var i = 0; i < totalFiles; i++){
	       var file = document.getElementById("file").files[i];
	       formData.append("file", file);
	   }	   
	    $.ajax({
	        url: '/update',
	        type: 'POST',
	        data: formData,
	        cache : false,
	        contentType : false,
	        processData : false,
	        success: function (data) {
	        	listData();
	        	$('#addfileModal').modal('hide'); 
	            alert(data.message);
	            cleanForm();
	        },
	        error: function (err) {
	            alert(JSON.stringify(err));
	        }
	    });
	
}

function deleteDroolsFile(i, refRowIndex){		
	  if (confirm('Want to delete?')) {
		var scope = $("#droolsFileListTable_tBody");
        var id= scope.find('#id'+refRowIndex).text();      
        $($(i).closest("tr")).remove();
		$.ajax({ 
	          type: 'GET',
	          url: "/delete/"+id,
		          success: function (data) {
		          	alert(data);  
		          },
		          error: function (err) {
		              alert(JSON.stringify(err));
		          }
		      });	 
	    }
}

function closeModal(){
	$('#addfileModal').modal('hide'); 
	cleanForm();
}

function cleanForm(){
	var scope = $("#addfileModal");	
	scope.find('#title').val("");
	scope.find('#file').val("");
	scope.find('#id').val("");
}
	
$(document).ready(function(){ 	
	listData();
});