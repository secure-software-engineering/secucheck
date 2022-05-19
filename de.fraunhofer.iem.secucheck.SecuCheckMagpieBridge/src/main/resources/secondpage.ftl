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
        .jumbotron {
            background-color: #363594;
            color: white;
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

        .form-control-clear {
            z-index: 10;
            pointer-events: auto;
            cursor: pointer;
        }
    </style>
    <script>
        function getFullURL(path, formID) {
            const fullURL = 'http://localhost:' + window.location.port + "/" + path;

            document.getElementById(formID).action = fullURL;
        }

        function searchFunction(myInput, myUL, clearer) {
            // Declare variables
            let myClearer = document.getElementById(clearer);
            myClearer.classList.remove('hidden');

            let input, filter, ul, li, a, i, txtValue;
            input = document.getElementById(myInput);
            filter = input.value.toUpperCase();
            ul = document.getElementById(myUL);
            li = ul.getElementsByTagName('li');

            // Loop through all list items, and hide those who don't match the search query
            for (i = 0; i < li.length; i++) {
                a = li[i].getElementsByTagName("a")[0];
                txtValue = a.textContent || a.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    li[i].style.display = "";
                } else {
                    li[i].style.display = "none";
                }
            }
        }

        function sendRunAnalysisRequest(path) {
            let xhr = new XMLHttpRequest();

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    //alert(xhr.response);
                }
            }

            xhr.open('get', 'http://localhost:' + window.location.port + path, true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
            xhr.send();
        }

        function selectAllCheckBoxes() {
            let id = $('.tab-content .active').attr('id');

            let chkBoxes = document.getElementsByClassName(id + "Class");

            for (let j = chkBoxes.length - 1; j >= 0; j--) {
                chkBoxes[j].checked = true;
            }
        }

        function unSelectAllCheckBoxes() {
            let id = $('.tab-content .active').attr('id');

            let chkBoxes = document.getElementsByClassName(id + "Class");

            for (let j = chkBoxes.length - 1; j >= 0; j--) {
                chkBoxes[j].checked = false;
            }
        }

        function clearSearchBox(searchBoxID, clearer, myUL) {
            let myClearer = document.getElementById(clearer);
            myClearer.classList.add('hidden');

            let input = document.getElementById(searchBoxID);
            input.value = '';

            // bring all the list items display on
            let ul = document.getElementById(myUL);
            let li = ul.getElementsByTagName('li');

            for (let i = 0; i < li.length; i++) {
                li[i].style.display = "";
            }
        }
    </script>
</head>
<body>
<!-- CONTAINERS -->
<!-- container puts padding around itself while container-fluid fills the whole screen. Bootstap grids require a container. -->
<div class="container" style="word-wrap: break-word;">
    <!-- page-header adds space aroundtext and enlarges it. It also adds an underline at the end -->
    <div class="page-header">
        <h1 style="font-family: 'Impact, Charcoal, sans-serif'; text-shadow: 1px 1px 15px #a6a2a2;text-align:center"><i><b>SecuCheck-Kotlin</b></br>${projectName}
            </i></h1>
    </div>
    <!-- jumbotron enlarges fonts and puts everything in a gray box with rounded corners. If jumbotron is outside a container it fills the total width. You can change the styles by placing the changes after the Bootstrap CDN link -->
    <div class="jumbotron">
        <!-- BUTTONS -->
        <!-- There are many built in button colors and sizes. If a link is to trigger in page functionality it should use role="button". The button element should be used in most situations -->
        <p>
            <!-- You can group buttons. You can size the buttons with btn-group-lg, btn-group-sm, or btn-group-xs -->
        <div class="btn-group btn-group-lg" role="group" aria-label="..."
             style="justify-content:center;align-items:center;">
            <button class="btn btn-default btn3d" style="background-color:#2E2D88;color:#FFFFFF;opacity: 0.8;"
                    form="secondForm" type="submit">Submit Configuration
            </button>
            <button class="btn btn-default" style="background-color:#2E2D88;color:#FFFFFF;opacity: 0.8;"
                    id="analysisBtn" onclick="sendRunAnalysisRequest('/runAnalysis')">Run Analysis
            </button>
            <button class="btn btn-default" style="background-color:#2E2D88;color:#FFFFFF;opacity: 0.8;" id="cancelBtn"
                    onclick="sendRunAnalysisRequest('/cancelAnalysis')">Cancel Analysis
            </button>
            <button class="btn btn-default" style="background-color:#2E2D88;color:#FFFFFF;opacity: 0.8;" id="clearBtn"
                    onclick="sendRunAnalysisRequest('/clearResultMarkers')">Clear Result Markers
            </button>
            <button type="button" class="btn btn-default" style="background-color:#2E2D88;color:#FFFFFF;opacity: 0.8;"
                    onclick="selectAllCheckBoxes()">Select All
            </button>
            <button type="button" class="btn btn-default" style="background-color:#2E2D88;color:#FFFFFF;opacity: 0.8;"
                    onclick="unSelectAllCheckBoxes()">Deselect All
            </button>
        </div>
        </p>
    </div>
</div>
<div class="container" style="word-wrap: break-word;">
    <!-- Use nav-pills, nav-tabs. To center tabs add nav-justified to the class for ul -->
    <ul class="nav nav-pills" style="font-size:18px">
        <li class="active"><a data-toggle="tab" href="#fluentSpec">FluentTQL Specifications</a></li>
        <li><a data-toggle="tab" href="#entryPoints">Entry Points</a></li>
        <li><a data-toggle="tab" href="#analysisSolver">Analysis Solver</a></li>
    </ul>
    <div class="tab-content">
        <iframe name="myForm" style="display:none;"></iframe>
        <form id="secondForm" method="POST" onsubmit="getFullURL('configSubmit', 'secondForm')" target="myForm">
            <div class="tab-content"
                 style="border-bottom: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;">
                <div id="fluentSpec" class="tab-pane fade in active checkboxes" style="font-size: 18px">
                    <div class="form-group has-feedback has-clear">
                        <input type="text" id="fluentSpecsInput"
                               onkeyup="searchFunction('fluentSpecsInput', 'fluentSpecsLists', 'fluentSpecsInputClearer')"
                               placeholder="Search for FluentTQL spec..." class="form-control">
                        <span id="fluentSpecsInputClearer"
                              class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden"
                              onclick="clearSearchBox('fluentSpecsInput', 'fluentSpecsInputClearer', 'fluentSpecsLists')"></span>
                    </div>

                    <!-- This is the FluentTQL specification tab -->
                    ${specDiv}
                </div>
                <div id="entryPoints" class="tab-pane fade checkboxes" style="font-size: 18px">
                    <div class="form-group has-feedback has-clear">
                        <input type="text" id="entryPointsInput"
                               onkeyup="searchFunction('entryPointsInput', 'entryPointsLists', 'entryPointsInputClearer')"
                               placeholder="Search for Entrypoints..." class="form-control">
                        <span id="entryPointsInputClearer"
                              class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden"
                              onclick="clearSearchBox('entryPointsInput', 'entryPointsInputClearer', 'entryPointsLists')"></span>
                    </div>

                    <!-- This is the Analysis entry points tab -->
                    ${entryPointDiv}
                </div>
                <div id="analysisSolver" class="tab-pane fade" style="font-size: 18px">
                    <label class="radio-inline">
                        <input type="radio" id="boomerang" value="boomerang" name="solverOption" checked>Boomerang 3.x
                    </label>
                    <label class="radio-inline">
                        <input type="radio" id="flowdroid" value="flowdroid" name="solverOption">FlowDroid
                    </label>
                </div>
            </div>
        </form>
    </div>
</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>