/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function sendReview(){
    
    var comment="", rate=0, ssid="";
    var i= $("#hidden-index").text();
    var comm="#"+i;
    comment= $(comm).val();
    dim= $("span.filled-stars").css("width");
    
    switch(dim){
        case "170px": rate=5; break;
        case "127.5px": rate=4; break;
        case "85px": rate=3; break;
        case "42.5px": rate=2; break;
        case "0px": rate=0; break;    
    }
    
    ssid= $("#hssid"+i).text();
    alert("comment: "+comment +"\nrate: "+rate+"\nssid: "+ssid);
    $.ajax({
        type: "GET",
        url: "./Controller",
        headers:{
            Accept: "application/json;charset=utf-8"
        },
        data: {service: 'insertFeedback', comment: comment, rate: rate, ssid: ssid},
        succes: function(response){
            alert(response);
            if(response==="OK"){
                alert("Thanks for your opinion!");
            }
            else{
                alert("Opinion not received. Please try again!");
            }
        },
        error: function(){
            alert("Chiamata fallita!");
            
        }  
        
    });
    return false;
}
