    // real time search for table
	
    document.getElementById("search_content").addEventListener("keyup", function()  {
    	var input, filter, table, tr, td, i;
    	input = document.getElementById("search_content");
    	filter = input.value.toUpperCase();
    	table = document.getElementById("data_table");
    	tr = table.getElementsByTagName("tr");

    	for (i = 0; i < tr.length; i++) {
    		td = tr[i].getElementsByTagName("th")[0];

    		if (td) {
    			if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
    				tr[i].style.display = "";
    			} else {
    				tr[i].style.display = "none";
    			}
    		}
    	}
    });
    
    //real time search for accounts
    document.getElementById("search_content").addEventListener("keyup", function()  {
    	var input, filter, table, tr, td, i;
    	input = document.getElementById("search_content");
    	filter = input.value.toUpperCase();
    	table = document.getElementById("acc-table");
    	tr = table.getElementsByTagName("tr");

    	for (i = 0; i < tr.length; i++) {
    		td = tr[i].getElementsByTagName("th")[0];

    		if (td) {
    			if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
    				tr[i].style.display = "";
    			} else {
    				tr[i].style.display = "none";
    			}
    		}
    	}
    });
    
  //real time search for planned_transactions
    document.getElementById("search_planned_transaction").addEventListener("keyup", function()  {
    	var input, filter, table, tr, td, i;
    	input = document.getElementById("search_planned_transaction");
    	filter = input.value.toUpperCase();
    	table = document.getElementById("trans_table");
    	tr = table.getElementsByTagName("tr");

    	for (i = 0; i < tr.length; i++) {
    		td = tr[i].getElementsByTagName("th")[0];

    		if (td) {
    			if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
    				tr[i].style.display = "";
    			} else {
    				tr[i].style.display = "none";
    			}
    		}
    	}
    });
    
    
   // Date picker 
    
    
    var dateToday = new Date(); 
    $(function() {
        $( "#addplanned_transaction_date" ).datepicker({
            numberOfMonths: 6,
            showButtonPanel: true,
            minDate: dateToday
        });
    });
    
    
    
    $( "#addplanned_transaction_date" ).datepicker({
    	format: 'yyyy-mm-dd'
     });
    
    

	
	