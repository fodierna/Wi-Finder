//API KEY GOOGLE AIzaSyDHbFTaIZIzKR7gu3bGBvtPYFgr8yFzxkY

function initMap(){
    
}

$(() => {
    initMap = function() {
    
    }
})

function getLocation(){
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(getPos, showError);
    }
}

function getPos(position){
    $("#lat").html('');
    $("#lon").html('');
    $("#lat").html(position.coords.latitude);
    $("#lon").html(position.coords.longitude);
    getAddress(position.coords.latitude, position.coords.longitude,-1);
}

function getWiFiList(){
    var lat=$("#lat").text();
    var lon=$("#lon").text();
    var networks=[];
    $.ajax({
        type: "POST",
        url: "./Controller",
        headers:{
            Accept: "application/json; charset=utf-8"
        },	
        data: {service: 'wifiListByPosition', lat: lat, lon: lon},
        success: function(response){
            var risposta=(response);
            if(risposta.length==0){
                alert("Non ci sono hotspot nelle vicinanze");
            }
            else{
                $("#result-panel").empty();
                $("#result-panel").append("<div class='panel-heading' id='result-panel-heading'></div>");
                $("#result-panel-heading").append("<span class='panel-title'><strong class='h3'>"+$("#hidden-address").text()+"</strong></span>");
                $("#result-panel-heading").append("<span class='glyphicon glyphicon-map-marker'></span>");
                $("#result-panel-heading").append("<span class='glyphicon glyphicon-pencil'></span>");
                        
        
                $("#result-panel").append("<div class='panel-body' id='result-panel-body'></div>");
                 
                
                $("#result-panel-body").append("<div id='map_wrapper' ><div id='map_canvas' class='mapping' class='embed-responsive embed-responsive-16by9'></div></div>");       
            
                $("#result-panel-body").append("<div class='row medium-bg hidden-xs' id='wifi-list-descriptor'></div>");
                
                $("#wifi-list-descriptor").append("<div class='col-xs-6'>"+
                        "<h4>SSID:</h4>"+
                        "</div>");
                    
                $("#wifi-list-descriptor").append("<div class='col-xs-2'>"+
                        "<h4>Distance:</h4>"+
                        "</div>");
                    
                $("#wifi-list-descriptor").append("<div class='col-xs-4'>"+
                        "<h4>Rate:</h4>"+
                        "</div>");
                    
                       
            
                $("#result-panel-body").append("<div class='panel-group' id='accordion'></div>");
                markers.length=0;
                labelIndex=0;
                var zy=0;
                for(var i=0; i<risposta.length; i++){
                    
                    markers[0]=[lat, lon];
                    markers[i+1]=[risposta[i].latCoordinate, risposta[i].lonCoordinate];
                    networks[i]=risposta[i];
                    $("#hidden-ssid").append("<p id='hssid"+i+"' style='display:none;'>"+risposta[i].ssid+"</p>");
                    $("#accordion").append("<div class='panel panel-default' id='panel"+i+"'></div>");
                    $("#panel"+i).append("<div class='panel-heading' id='heading"+i+"'></div>");
                    $("#heading"+i).append("<a data-toggle='collapse' data-parent='#accordion' href='#collapse"+i+"' id='toggle"+i+"'></a>");
                    $("#toggle"+i).append("<div class='row' id='row"+i+"'></div>");
                    $("#row"+i).append(
                            "<div class='col-xs-6'>"+
                            "<h4 class='panel-title'>"+
                            risposta[i].ssid+" ("+labels[labelIndex++ % labels.length]+")"+
                            "</h4>"+
                            "</div>");
                                    
                    $("#row"+i).append(
                            "<div class='col-xs-2'>"+
                            parseInt(calculateDistance(risposta[i].latCoordinate,risposta[i].lonCoordinate, lat, lon), 10)+" mt"+
                            "</div>");
                                    
                                    
                    //calcolo media recensioni rete
                    
                    if(risposta[i].feedback.length>0){
                       
                        var sum=0;
                        $("#row"+i).append("<div class='col-xs-3' id='col-xs3"+i+"'></div>");
                        for(var j=0;j<risposta[i].feedback.length;j++){
                            sum+=risposta[i].feedback[j].rate;
                        }
                        var avg_rate=sum/risposta[i].feedback.length;
                        Math.round(avg_rate);
                    
                        for(var k=0; k<avg_rate; k++){
                            $("#col-xs3"+i).append("<span class='glyphicon glyphicon-star'></span>");
                        }

                        for(var w=0;w<5-k;w++){
                            $("#col-xs3"+i).append("<span class='glyphicon glyphicon-star-empty'></span>");
                        }
                        
                        
                       
                    }
                    else{$("#row"+i).append(
                                "<div class='col-xs-3'>"+
                                "Not rated yet"+
                                "</div>");
                    }
                       
                    $("#row"+i).append(
                            "<div class='col-xs-1'>"+
                            "<span class='glyphicon glyphicon-menu-down'></span>"+
                            "</div>");
                            
                    
                    
                   
                    
                            
                    $("#panel"+i).append("<div id='collapse"+i+"' class='panel-collapse collapse'></div>");
                    $("#collapse"+i).append("<div class='panel-body' id='panel-body-collapse"+i+"'></div>");
                    $("#panel-body-collapse"+i).append("<div class='row detail-first-row' id='detail-row"+i+"'></div>");
                                 
                    $("#detail-row"+i).append(
                            "<div class='col-xs-9'>"+
                            "<h4 id='network-address"+i+"'></h4>" +
                            "</div>");
                                            
                    $("#detail-row"+i).append(
                            "<div class='col-xs-3'>"+
                            "<button type='button' class='btn btn-primary'>Drive Indications</button>"+
                            "</div>");
                    
                   
                            
                    
                    $("#panel-body-collapse"+i).append("<div id='flex-div"+i+"'><h2 style='margin-left:20px;'>Review list</h2></div>");          
                    
                    
                    $("#feedback-div"+i).append("<div class='row is-flex' id='flex-row"+i+"'></div>");
                                      
                    
                    if(risposta[i].feedback.length>0){
                        
                        for(var z=0;z<risposta[i].feedback.length;z++){
                            console.log(risposta[i].feedback[z]);
                            console.log(i +" "+z);
                            
                            $("#panel-body-collapse"+i).append("<div class='well feedbacks-well' id='feedback-div"+zy+"'></div>");   
           
                            $("#feedback-div"+zy).append("<div>"+risposta[i].feedback[z].feedbackDate+"</div>");
                            $("#feedback-div"+zy).append("<div>");
                            
                            
                                
                            for(var x=0; x<risposta[i].feedback[z].rate; x++){
                                $("#feedback-div"+zy).append("<span class='glyphicon glyphicon-star'></span>");
                            }
                        
                            for(var o=0;o<5-risposta[i].feedback[z].rate;o++){
                                $("#feedback-div"+zy).append("<span class='glyphicon glyphicon-star-empty'></span>");
                            }
              
                            $("#feedback-div"+zy).append("</div>");
                            
                            
                            $("#feedback-div"+zy).append("<p>"+risposta[i].feedback[z].comment+"</p>")
                            $("#flex-row"+zy).append("<br>");
                            $("#flex-row"+zy).append("<br>"); 
                            zy++;
                        }
                        
                            
                                    
                        $("#panel-body-collapse"+i).append("<div class='well personal-well'>"+
                                "<div class='form-group'>"+
                                "<h4>Rating</h4>"+
                                "<input id='rating-system"+i+"' type='number' class='rating' min='1' max='5' step='1'/>"+
                                "<label for='comment"+i+"'>Comment</label>"+
                                "<textarea class='form-control' rows='5' id='"+i+"'></textarea>"+
                                "<br>"+
                                "<button class='btn btn-primary pull-right' onclick='sendReview(); return false'>Send review</button>"+
                                "<br>"+
                                "</div>"+
                                "</div><script src='javascript/star-rating.js'></script>");                     
                    }
                    
                    else
                    {
                        $("#flex-div"+i).append("There's no reviews yet");
                        $("#panel-body-collapse"+i).append(
                                "<div class='well personal-well'>"+
                                "<div class='form-group'>"+
                                "<h4>Rating</h4>"+
                                "<input id='rating-system"+i+"' type='number' class='rating' min='1' max='5' step='1'/>"+
                                "<div><h4>Comment</h4></div>"+
                                "<textarea class='form-control' rows='5' id='"+i+"'></textarea>"+
                                "<br>"+
                                "<button class='btn btn-primary pull-right' onclick='sendReview(); return false'>Send review</button>"+
                                "<br>"+
                                "</div>"+
                                "</div><script src='javascript/star-rating.js'></script>");              
                        
                    }
                }    
            }
            initialize();
            for(var h=0; h<networks.length;h++){
                getAddress(networks[h].latCoordinate, networks[h].lonCoordinate,h);
            }
            
        
        },
        error: function(){
            alert("Chiamata fallita!");
        }  
    });
    return false;
}




function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            alert("User denied the request for Geolocation.");
            break;
        case error.POSITION_UNAVAILABLE:
            alert("Location information is unavailable.");
            break;
        case error.TIMEOUT:
            alert("The request to get user location timed out.");
            break;
        case error.UNKNOWN_ERROR:
            alert("An unknown error occurred.");
            break;
    }
}


function calculateDistance(lat1,lon1,lat2,lon2) {
    var R = 6371; // Radius of the earth in meters
    var dLat = deg2rad(lat2-lat1);  // deg2rad below
    var dLon = deg2rad(lon2-lon1); 
    var a = 
            Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
            Math.sin(dLon/2) * Math.sin(dLon/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
    return R * c * 1000; // Distance in mt
    
}

function deg2rad(deg) {
    return deg * (Math.PI/180);
}


function getAddress(lat, lon, i){
    var latlng = new google.maps.LatLng(lat, lon);
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({ 'latLng': latlng }, function (results, status) {
       
        if (status == google.maps.GeocoderStatus.OK) {
            console.log(results);
            if(results){
                var address = (results[0].formatted_address);
                $("#hidden-address").html("");
                $("#hidden-address").html(address);
                if(i>=0){
                    $("#network-address"+i).html($("#hidden-address").text());   
                }
            }
            else
                altert("Errore su results");
        }
        else
            alert("Errore su status");
    });
}

