<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">

   <!-- If IE use the latest rendering engine -->
   <meta http-equiv="X-UA-Compatible" content="IE=edge">

   <!-- Set the page to the width of the device and set the zoon level -->
   <meta name="viewport" content="width = device-width, initial-scale = 1">
   <title>${projectName}</title>
   <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

   <style>
      .jumbotron{
         background-color:#2E2D88;
         color:white;
         padding: 1px 1px 1px 1px;
         text-align: center;
      }
      /* Adds borders for tabs */
      .tab-content {
         border-left: 1px solid #ddd;
         border-right: 1px solid #ddd;
         border-bottom: 1px solid #ddd;
         padding: 10px;
      }
      .nav-tabs {
         margin-bottom: 0;
      }
   </style>

</head>
<body>

<!-- CONTAINERS -->
<!-- container puts padding around itself while container-fluid fills the whole screen. Bootstap grids require a container. -->
<div class="container" style="word-wrap: break-word;">

   <!-- page-header adds space aroundtext and enlarges it. It also adds an underline at the end -->
   <div class="page-header">
      <h1 style="font-family: 'Impact, Charcoal, sans-serif'; text-shadow: 1px 1px 15px #a6a2a2;text-align:center"><i><b>SecuCheck</b></br>${projectName}</i></h1>
   </div>
</div>

<div class="container" style="word-wrap: break-word;">
   <!-- Use nav-pills, nav-tabs. To center tabs add nav-justified to the class for ul -->
   <ul class="nav nav-pills" style="font-size: 18px">
      <li class="active"><a data-toggle="tab" href="#fluentSpec">FluentTQL Specifications jar path</a></li>
   </ul>

   <div class="tab-content">
      <div id="fluentSpec" class="tab-pane fade in active" style="text-align:center;">
         <form id="specform" method="POST" action="/specPathResponse" class="navbar-form" role="search">
            <div class="form-group" style="font-size: 18px">
               <input type="text" id="specpath" name="specPath" spellcheck="false" class="form-control" placeholder="Absolute path to Directory containing compiled Jar" size=50>
            </div>
            </br>
            <button style="margin-top:1em;margin-right:1em;padding:0.5em 1em 0.5em 1em;" type="submit" class="btn btn-default">Submit</button>
            <button style="margin-top:1em;padding:0.5em 1em 0.5em 1em;" type="reset" class="btn btn-default">Reset</button>
         </form>
      </div>
   </div>

</div>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>