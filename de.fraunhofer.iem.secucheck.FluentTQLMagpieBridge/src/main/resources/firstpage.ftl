<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
   <head>
      <title>${projectName}</title>
      <meta name="viewport" content="width=devide-width, initial-scale=1.0">
      <meta charset="utf-8">
      <link rel="stylesheet" type="text/css" href="static/app.css">
      <script type = "text/javascript">
         history.pushState(null, null, location.href);
         history.back();
         history.forward();
         window.onpopstate = function ()
         {
            history.go(1);
         };
      </script>
   </head>
   <body>
      <div class="secu-container">
         <div class="logo-header">
            <img src="img/secu.png">
         </div>
         <div class="app-name-header">
            <h1 style="font-family: 'Impact, Charcoal, sans-serif'; text-shadow: 1px 1px 15px black; color: white; margin-left: 1.5%">
               <i>
               Secucheck --- 
               </i>
               ${projectName}<br>
               <button class="btn btn--pill btn--green" form="specform" type="submit">Submit</button>
               <button class="btn btn--pill btn--green" form="specform" type="reset">Reset</button>
            </h1>
         </div>
         <div class="menu-header" style="grid-row: 3 / span 1;grid-column: 1 / span 1;">
            <button class="menu-button" style="background-color: #5b5ea6;color: #EEFBFB;">
            FluentTQL Specification's path
            </button>
         </div>
         <div class="menu-empty-space menu-empty-space-row1">
         </div>
         <div class="page-content-without-grid">
            <form id="specform" method="POST" action="/specPathResponse" style="margin-top: 2%; margin-left: 2%">
               <label style="font-size: 20px"><i>FluentTQL Specification's path (Directory containing compiled Jar):</i></label>
               <div class="input-group">
                  <input
                  class="input--style-3"
                  id="specpath"
                  type="text"
                  placeholder="Absolute path to Directory containing compiled Jar"
                  name="specPath"
                  spellcheck="false">
               </div>
            </form>
         </div>
      </div>
   </body>
</html>