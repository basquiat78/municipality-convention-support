<!doctype html>
<html lang="en">
	<head>
    	<title>Upload CSV File</title>

		<meta charset="utf-8"/>
    	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    	<meta name="description" content="Upload View">
    	<meta name="author" content="basquiat">
 		<script src="/js/jquery.min.js"></script>
 		<script src="/js/jquery.form.min.js"></script>
 		<script>
 		
 			$(function () {
        		$('#uploadBtn').click(function (event) {
        		
	            	$('#upload').ajaxForm({
		                url: '../api/v1/upload',
		                type: 'POST',
		                dataType: 'text',
		                enctype: 'multipart/form-data',
		                contentType: 'multipart/form-data; charset=UTF-8',
		                success: function (data) {
		                    alert(data);
		                },
		                error: function (request, status, error) {
		                    alert(error);
		                }
            		}).submit();
            		// 이벤트 버블이 생겨서 해당 코드 삽입
            		event.preventDefault();
				});
			});
 		</script>
	</head>

<body>
<div>
<h2>Bootstrap File upload demo</h2>
	<div>
		<form name="upload" id="upload" method="post">
			<div><input type="file" name="file" /></div>
			<div><input type="submit" id="uploadBtn" value="Upload" /></div>
		</form>
	</div>
</div>
</body>
</html>