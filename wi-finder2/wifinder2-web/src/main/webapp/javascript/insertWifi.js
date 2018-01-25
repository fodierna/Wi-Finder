

function showForm(){
    $("#ppp").css("display", "block");
    $("#insertButton").css("display","none");
    
}

function newWifi(){
    var ssid= $("#inputFssid").val();
    var description=$("#inputFdescription").val();
    var lat=$("#lat").text();
    var lon=$("#lon").text();
    var userId=1;

    $("#insertButton").css("display", "block");
    $("#ppp").css("display", "none");
    $.ajax({
        type: "GET",
        url: "./Controller",
        headers:{
            Accept: "application/json; charset=utf-8"
        },	
        data: {service: 'insertWiFi', ssid: ssid, lat: lat, lon: lon, description: description, userId: userId},
        success: function(response){
            if(response==="OK"){
                alert("Thank for your collaboration!");
            }
            else{
                alert("Something went wrong! Please try again");
            }
        },
        error: function(){
            alert("Request failed!");
        }  
        
    });
}



