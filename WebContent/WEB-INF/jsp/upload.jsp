<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Stats Upload</title>
        <base href="/Stats/"/>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
       	<link rel="stylesheet" href="http://www.watfordcheetahs.com/css/main.css" />
    </head>
    <body>
    	<div id="container">
	        <h1 class="title">Stats Upload</h1>
	        <div id=content-full>
		        <div class="bordered">
		        	<form id="uploadform" method="post" enctype="multipart/form-data">
			            <table style="width: 100%;">
			            	<tr>
			            		<td style="width: 100%;">
			            			<input type="text" name="name"/>
			            			<input type="file" name="file" id="fileinput"/>
			            		</td>
			            	</tr>
			            	<tr>
			            		<td>Season: 
			            			<select id="season" onchange="updateWeekSelect()">
			            				<c:forEach var="season" items="${seasons}">
                							<option value="${season.seasonid}">${season.year}</option>
              							</c:forEach>
			            			</select>
			            			Week: 
			            			<select id="weeknum">
			            				<option>&lt;Please select a season&gt;</option>
			            			</select>
			            		</td>
			            	</tr>
			            	<tr>
			            		<td>
			            			<input type="submit"/>
			            		</td>
			            	</tr>	
			            </table>
			        </form>
		        </div>
		        <div id="results" class="bordered"></div>
	        </div>  
        </div>      
        <script>$('#uploadform').submit(function(event) {
            	event.preventDefault();

				var formData = new FormData();
				var fileinput = $('#fileinput');
				formData.append('file', fileinput[0].files[0]);
				formData.append('season', $('#season').val());
				formData.append('weeknum', $('#weeknum').val());
				
				$.ajax({
				    url: '/Stats/upload',
				    data: formData,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    success: function(resp){
						$('#results').empty().html(resp.result);
				    }
				});								
				
				return false;
            });
        
        	function updateWeekSelect() {
        		var season = $('#season').val();
        		
        		$.getJSON('upload/seasons/' + season,
        			function(weeks) {
        				var weekSelect = $('#weeknum');
        				$(weekSelect).empty();
        				for(var i = 0; i < weeks.length; i++) {
        					var week = weeks[i];
        					$(weekSelect).append( $('<option>', {value: week.weeknum }).text(week.displayName));	
        				}
        			}
        		);
        	}
        </script>
    </body>
</html>