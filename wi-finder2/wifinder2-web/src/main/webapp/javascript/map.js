var markers= [];
var labels='ABCDEFGHIJKLMNOPQRSTUVWXYZ';
var labelIndex=0;


function initialize() {
    labelIndex=0;
    if(markers.length>0){
    
        var map;
        var bounds = new google.maps.LatLngBounds();
        var mapOptions = {
            mapTypeId: 'roadmap'
        };
                    
        // Display a map on the page
        map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
        map.setTilt(45);
        
   
         
        var position = new google.maps.LatLng(markers[0][0], markers[0][1]);
        bounds.extend(position);
        marker = new google.maps.Marker({
            position: position,
            icon: {
                path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
                scale: 3
            },
            map: map
        });
        marker.setMap(map);
        
        for(var ii = 1; ii < markers.length; ii++ ) {
            position = new google.maps.LatLng(markers[ii][0], markers[ii][1]);
            bounds.extend(position);
            marker = new google.maps.Marker({
                position: position,
                map: map,
                label: labels[labelIndex++ % labels.length]
            });
            marker.setMap(map);
        }
        
        // Automatically center the map fitting all markers on the screen
        map.fitBounds(bounds);
    
    

        // Override our map zoom level once our fitBounds function runs (Make sure it only runs once)
        var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function(event) {
            this.setZoom(16);
            google.maps.event.removeListener(boundsListener);
        });
    }
    else
        alert("Marker non pronti");
    
}
