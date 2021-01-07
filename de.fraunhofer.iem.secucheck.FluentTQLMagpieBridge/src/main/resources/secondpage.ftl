<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
   <head>
      <title>${projectName}</title>
      <meta name="viewport" content="width=devide-width, initial-scale=1.0">
      <meta charset="utf-8">
      <link rel="stylesheet" type="text/css" href="static/app.css">

      <!-- Latest compiled and minified JavaScript -->
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

      <script type="text/javascript">
         function switchTabs(switchToID) {
            var el = document.getElementsByClassName("checkboxes");

            for (var i = 0; i < el.length; i++) {
               if (el[i].id == switchToID) {
                  el[i].style.display = 'inline-block';
               } else {
                  el[i].style.display = 'none';
               }
            }

            var btn_el = document.getElementsByClassName("menu-button");
            for (var i = 0; i < btn_el.length; i++) {
               if (btn_el[i].id == (switchToID + "Btn")) {
                  btn_el[i].style.backgroundColor = '#5b5ea6';
               } else {
                  btn_el[i].style.backgroundColor = '#707070';
               }
            }
         }

         function selectAllCheckBoxes() {
            var el = document.getElementsByClassName("checkboxes");

            for (var i = 0; i < el.length; i++) {
               if (el[i].style.display != 'none') {
                  var chkBoxes = document.getElementsByClassName(el[i].id + "Class");

                  for (var j = chkBoxes.length - 1; j >= 0; j--) {
                     chkBoxes[j].checked = true;
                  }
               }
            }
         }

         function unSelectAllCheckBoxes() {
            var el = document.getElementsByClassName("checkboxes");

            for (var i = 0; i < el.length; i++) {
               if (el[i].style.display != 'none') {
                  var chkBoxes = document.getElementsByClassName(el[i].id + "Class");

                  for (var j = chkBoxes.length - 1; j >= 0; j--) {
                     chkBoxes[j].checked = false;
                  }
               }
            }
         }
      </script>
   </head>
   <body data-new-gr-c-s-check-loaded="14.990.0" data-gr-ext-installed="">
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
               <button class="btn btn--pill btn--green" form="secondForm" type="submit">Submit Configuration</button>
               <button class="btn btn--pill btn--green" form="secondForm">Run Analysis</button>
               <button class="btn btn--pill btn--green" type="button" onclick="selectAllCheckBoxes()">Select All</button>
               <button class="btn btn--pill btn--green" type="button" onclick="unSelectAllCheckBoxes()">De-Select All</button>
            </h1>
         </div>
         <div class="menu-header" style="grid-row: 3 / span 1;grid-column: 1 / span 1;">
            <button class="menu-button" style="background-color: #5b5ea6;" id="fluentSpecBtn" onclick="switchTabs('fluentSpec')">
            FluentTQL Specifications
            </button>
         </div>
         <div class="menu-header" style="grid-row: 4 / span 1;grid-column: 1 / span 1;">
            <button class="menu-button" id="entryPointsBtn" onclick="switchTabs('entryPoints')">
               Analysis Entry points
            </button>
         </div>
         <div class="menu-empty-space menu-empty-space-row2">
         </div>
         <div class="page-content-without-grid">
            <style>
               .hide { position:absolute; top:-1px; left:-1px; width:1px; height:1px; }
            </style>

            <iframe name="hiddenFrame" class="hide"></iframe>
            <form id="secondForm" method="POST" action="/configSubmit" style="margin-top: 2%; margin-left: 2%;" target="hiddenFrame">
               <!-- This is the FluentTQL specification tab -->

               ${specDiv}





               <!-- This is the Analysis entry points tab -->
               ${entryPointDiv}
            </form>
         </div>
      </div>
   </body>
</html>