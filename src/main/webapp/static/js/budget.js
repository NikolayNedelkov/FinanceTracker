
    // Update and display the itemlog  ##need modify
    function displayItem() {
        // Check login status
        if ($("#loginstatus").html() != "Guest") {
            // Post data to php
            $.ajax({
                    method: "POST",
                    url: "displayItem.php",
                    data: { token_post: token }
                })
                .done(function(data) {
                    // If there's illegal access
                    if (data.success == false) {
                        // Clear the itemlog
                        $('.itemlog').empty();
                        fake();
                    } else {
                        console.log(data);

                        // Clear the itemlog and make the fake item
                        $('.itemlog').empty();
                        fake();

                        // Income: success
                        // Expense: warning
                        // Debt: info
                        // Credit: default

                        // Prepare the insert div
                        $success = '<li class="list-group-item list-group-item-success"'; // add item_id
                        $warning = '<li class="list-group-item list-group-item-warning"'; // add item_id
                        $info = '<li class="list-group-item list-group-item-info"'; // add item_id
                        $default = '<li class="list-group-item list-group-item-default"'; // add item_id
                        $name = '<div class="inline item_name">';
                        $name_debt = '<div class="inline item_name">Debt to: ';
                        $name_credit = '<div class="inline item_name">Credit from: ';
                        $amt = '<div class="inline item_amt">$ ';
                        $category = '<div class="inline item_category">Category: ';
                        $note = '<div class="inline item_note">Note: ';

                        // Run through the item array
                        for (var i = 0; i < data.length; i++) {
                            // Check the date
                            if (data[i].item_date == $("#current_date").html()) {
                                // If the category selected is all, or matches the item category
                                if ($("#display_option option:selected").val() == "all" || $("#display_option option:selected").val() == data[i].item_category.toLowerCase()) {
                                    if (data[i].item_type == "income") {
                                        $('.itemlog').append($success + 'id ="' + data[i].item_id + '">' + $name + data[i].item_name + '</div>' + $amt + data[i].item_amt + '</div>' + $category + data[i].item_category + '</div>' + $note + data[i].item_note + '</div></li>');
                                    }
                                    if (data[i].item_type == "expense") {
                                        $('.itemlog').append($warning + 'id ="' + data[i].item_id + '">' + $name + data[i].item_name + '</div>' + $amt + data[i].item_amt + '</div>' + $category + data[i].item_category + '</div>' + $note + data[i].item_note + '</div></li>');
                                    }
                                    if (data[i].item_type == "debt") {
                                        $('.itemlog').append($info + 'id ="' + data[i].item_id + '">' + $name_debt + data[i].item_name + '</div>' + $amt + data[i].item_amt + '</div>' + $category + data[i].item_category + '</div>' + $note + data[i].item_note + '</div></li>');
                                    }
                                    if (data[i].item_type == "credit") {
                                        $('.itemlog').append($default + 'id ="' + data[i].item_id + '">' + $name_credit + data[i].item_name + '</div>' + $amt + data[i].item_amt + '</div>' + $category + data[i].item_category + '</div>' + $note + data[i].item_note + '</div></div>');
                                    }
                                } // End of category check
                                else {
                                    // do nothing
                                }
                            } // End of date check
                            else {
                                // do nothing
                            }
                        } // End of for loop
                    } // End of else
                }); // End of done
        } // End of if

        // Call the search function
        search();
    } // End of displayItem function
    $("#display_option").change(displayItem);






    // Open the edit expense modal when the item is clicked
    $(document).on('click', '.list-group-item.list-group-item-warning', function(e) {
        // // Put the original content into the editbox
        // foreditexpense_name = $(this).attr('expense_name');
        // foreditexpense_amt = $(this).attr('expense_amt');
        // foreditexpense_note = $(this).attr('expense_note');

        // $('#editexpense_name').attr('placeholder', foreditexpense_name);
        // $('#editexpense_amt').attr('placeholder', foreditexpense_amt);
        // $('#editexpense_note').attr('placeholder', foreditexpense_note);

        // Get the expense id
        editexpense_id = $(this).attr('id');

        // Open the modal
        $("#editExpenseModal").modal();

    });

    // Edit expense
    $("#editExpenseOn_btn").on('click', function() {
        // Check login status
        if ($("#loginstatus").html() == "Guest") {
            alert("Please log in first!");
        } else {
            var editexpense_name = $('#editexpense_name').val();
            var editexpense_amt = $('#editexpense_amt').val();
            // console.log(editexpense_id);

            // Check if the input fields are empty
            if (editexpense_name == "" || editexpense_amt == "") {
                alert("Please enter the item name and amount");
            } else {
                var editexpense_category = $("#editExpense_category_option option:selected").val();
                var editexpense_note = $('#editexpense_note').val();

                // console.log(expense_name);
                // console.log(expense_amt);
                // console.log(expense_category);
                // console.log(expense_note);

                // Post data to php
                $.ajax({
                        method: "POST",
                        url: "editExpense.php",
                        data: { expense_id: editexpense_id, expense_name: editexpense_name, expense_amt: editexpense_amt, expense_category: editexpense_category, expense_note: editexpense_note, token_post: token }
                    })
                    .done(function(data) {
                        if (data.success == false) {
                            alert(data.message);
                        } else {
                            // If success...
                            alert(data.message); // "Expense edited successfully"

                            // Close the modal
                            $("#editExpenseModal").modal('hide');

                            // Update the itemlog
                            displayItem();
                        }
                    });

            }
        }
    }); // End of editExpense function

    // Delete expense
    $("#deleteExpenseOn_btn").on('click', function() {
        // Check login status
        if ($("#loginstatus").html() == "Guest") {
            alert("Please log in first!");
        } else {
            // console.log(editexpense_id);

            // Post data to php
            $.ajax({
                    method: "POST",
                    url: "deleteExpense.php",
                    data: { expense_id: editexpense_id, token_post: token }
                })
                .done(function(data) {
                    if (data.success == false) {
                        alert(data.message);
                    } else {
                        // If success...
                        alert(data.message); // "Expense edited successfully"

                        // Close the modal
                        $("#editExpenseModal").modal('hide');

                        // Update the itemlog
                        displayItem();
                    }
                });

        }
    }); // End of deleteExpense function


    // Open the edit income modal when the item is clicked
    $(document).on('click', '.list-group-item.list-group-item-success', function(e) {
        // Get the income id
        editincome_id = $(this).attr('id');

        // Open the modal
        $("#editIncomeModal").modal();

    });

    // Edit income
    $("#editIncomeOn_btn").on('click', function() {
        // Check login status
        if ($("#loginstatus").html() == "Guest") {
            alert("Please log in first!");
        } else {
            var editincome_name = $('#editincome_name').val();
            var editincome_amt = $('#editincome_amt').val();
            // console.log(editincome_id);

            // Check if the input fields are empty
            if (editincome_name == "" || editincome_amt == "") {
                alert("Please enter the item name and amount");
            } else {
                var editincome_category = $("#editIncome_category_option option:selected").val();
                var editincome_note = $('#editincome_note').val();

                // Post data to php
                $.ajax({
                        method: "POST",
                        url: "editIncome.php",
                        data: { income_id: editincome_id, income_name: editincome_name, income_amt: editincome_amt, income_category: editincome_category, income_note: editincome_note, token_post: token }
                    })
                    .done(function(data) {
                        if (data.success == false) {
                            alert(data.message);
                        } else {
                            // If success...
                            alert(data.message); // "Expense edited successfully"

                            // Close the modal
                            $("#editIncomeModal").modal('hide');

                            // Update the itemlog
                            displayItem();
                        }
                    });

            }
        }
    }); // End of editIncome function

    // Delete income
    $("#deleteIncomeOn_btn").on('click', function() {
        // Check login status
        if ($("#loginstatus").html() == "Guest") {
            alert("Please log in first!");
        } else {
            // console.log(editincome_id);

            // Post data to php
            $.ajax({
                    method: "POST",
                    url: "deleteIncome.php",
                    data: { income_id: editincome_id, token_post: token }
                })
                .done(function(data) {
                    if (data.success == false) {
                        alert(data.message);
                    } else {
                        // If success...
                        alert(data.message); // "Expense edited successfully"

                        // Close the modal
                        $("#editIncomeModal").modal('hide');

                        // Update the itemlog
                        displayItem();
                    }
                });

        }
    }); // End of deleteExpense function


    // Open the edit debt modal when the item is clicked
    $(document).on('click', '.list-group-item.list-group-item-info', function(e) {
        // Get the debt id
        editdebt_id = $(this).attr('id');
        debt_username = $(this).children(".item_name").html(); // Debt to: Username

        $("#editdebt_username").html(debt_username);

        // Open the modal
        $("#editDebtModal").modal();

    });
    // Edit debt
    $("#editDebtOn_btn").on('click', function() {
        // Check login status
        if ($("#loginstatus").html() == "Guest") {
            alert("Please log in first!");
        } else {
            var editdebt_amt = $('#editdebt_amt').val();
            // console.log(editdebt_id);

            // Check if the input fields are empty
            if (editdebt_amt == "") {
                alert("Please enter the amount");
            } else {
                var editdebt_note = $('#editdebt_note').val();

                // Post data to php
                $.ajax({
                        method: "POST",
                        url: "editDebt.php",
                        data: { debt_id: editdebt_id, debt_amt: editdebt_amt, debt_note: editdebt_note, token_post: token }
                    })
                    .done(function(data) {
                        if (data.success == false) {
                            alert(data.message);
                        } else {
                            // If success...
                            alert(data.message); // "Expense edited successfully"

                            // Close the modal
                            $("#editDebtModal").modal('hide');

                            // Update the itemlog
                            displayItem();
                        }
                    });

            }
        }
    }); // End of editDebt function

    // Delete debt
    $("#deleteDebtOn_btn").on('click', function() {
        // Check login status
        if ($("#loginstatus").html() == "Guest") {
            alert("Please log in first!");
        } else {

            // Post data to php
            $.ajax({
                    method: "POST",
                    url: "deleteDebt.php",
                    data: { debt_id: editdebt_id, token_post: token }
                })
                .done(function(data) {
                    if (data.success == false) {
                        alert(data.message);
                    } else {
                        // If success...
                        alert(data.message); // "Expense edited successfully"

                        // Close the modal
                        $("#editDebtModal").modal('hide');

                        // Update the itemlog
                        displayItem();
                    }
                });

        }
    }); // End of deleteDebt function




    // Open the edit credit modal when the item is clicked
    $(document).on('click', '.list-group-item.list-group-item-default', function(e) {
        // Get the credit id
        editcredit_id = $(this).attr('id');
        credit_username = $(this).children(".item_name").html(); // Debt to: Username

        $("#editcredit_username").html(credit_username);

        // Open the modal
        $("#editCreditModal").modal();

    });
    // Edit credit
    $("#editCreditOn_btn").on('click', function() {
        // Check login status
        if ($("#loginstatus").html() == "Guest") {
            alert("Please log in first!");
        } else {
            var editcredit_amt = $('#editcredit_amt').val();
            // console.log(editcredit_id);

            // Check if the input fields are empty
            if (editdebt_amt == "") {
                alert("Please enter the amount");
            } else {
                var editcredit_note = $('#editcredit_note').val();

                // Post data to php
                $.ajax({
                        method: "POST",
                        url: "editCredit.php",
                        data: { credit_id: editcredit_id, credit_amt: editcredit_amt, credit_note: editcredit_note, token_post: token }
                    })
                    .done(function(data) {
                        if (data.success == false) {
                            alert(data.message);
                        } else {
                            // If success...
                            alert(data.message); // "Expense edited successfully"

                            // Close the modal
                            $("#editCreditModal").modal('hide');

                            // Update the itemlog
                            displayItem();
                        }
                    });

            }
        }
    }); // End of editCredit function

    // Delete debt
    $("#deleteCreditOn_btn").on('click', function() {
        // Check login status
        if ($("#loginstatus").html() == "Guest") {
            alert("Please log in first!");
        } else {

            // Post data to php
            $.ajax({
                    method: "POST",
                    url: "deleteCredit.php",
                    data: { credit_id: editcredit_id, token_post: token }
                })
                .done(function(data) {
                    if (data.success == false) {
                        alert(data.message);
                    } else {
                        // If success...
                        alert(data.message); // "Expense edited successfully"

                        // Close the modal
                        $("#editCreditModal").modal('hide');

                        // Update the itemlog
                        displayItem();
                    }
                });

        }
    }); // End of deleteCredit function



    // Summary function
    function summary() {
        // Clear the log
        $("#totalexpense").empty();
        $("#totalincome").empty();
        $("#net").empty();

        // Get the num strings for expense
        var rawnums_expense = [];
        $(".list-group-item.list-group-item-warning").children(".item_amt").each(function(i, e) {
            rawnums_expense.push($(e).html());
        });
        // Parse the string to get ints
        var totalexpense = 0;
        for (var i = 0; i < rawnums_expense.length; i++) {
            rawnums_expense[i] = parseInt(rawnums_expense[i].replace(/[^0-9\.]/g, ''), 10); // gives the int part
            totalexpense = totalexpense + rawnums_expense[i];
        }


        // Get the num strings for income
        var rawnums_income = [];
        $(".list-group-item.list-group-item-success").children(".item_amt").each(function(i, e) {
            rawnums_income.push($(e).html());
        });
        // Parse the string to get ints
        var totalincome = 0;
        for (var i = 0; i < rawnums_income.length; i++) {
            rawnums_income[i] = parseInt(rawnums_income[i].replace(/[^0-9\.]/g, ''), 10); // gives the int part
            totalincome = totalincome + rawnums_income[i];
        }

        var totalnet = totalincome - totalexpense;


        // Hide the dashlog
        $(".dashlog").hide();
        // Display the summarylogs
        $(".summarylog").show();


        // Do the math
        $("#totalexpense").html(totalexpense);
        $("#totalincome").html(totalincome);
        $("#net").html(totalnet);
    }
    $("#summary_btn").click(summary);




    // Change background color function
    function changeBackColor() {
        if ($("#backcolor_option option:selected").val() == "White") {
            $('#page-wrapper').css('background-color', '#fff');
            $('body').css('background-color', '#fff');
        }

        if ($("#backcolor_option option:selected").val() == "Red") {
            $('#page-wrapper').css('background-color', '#ffe6e6');
            $('body').css('background-color', '#ffe6e6');
        }

        if ($("#backcolor_option option:selected").val() == "Orange") {
            $('#page-wrapper').css('background-color', '#fff0e6');
            $('body').css('background-color', '#fff0e6');
        }

        if ($("#backcolor_option option:selected").val() == "Yellow") {
            $('#page-wrapper').css('background-color', '#ffffe6');
            $('body').css('background-color', '#ffffe6');
        }

        if ($("#backcolor_option option:selected").val() == "Green") {
            $('#page-wrapper').css('background-color', '#eeffe6');
            $('body').css('background-color', '#eeffe6');
        }

        if ($("#backcolor_option option:selected").val() == "Blue") {
            $('#page-wrapper').css('background-color', '#e6ebff');
            $('body').css('background-color', '#e6ebff');
        }

        if ($("#backcolor_option option:selected").val() == "Pink") {
            $('#page-wrapper').css('background-color', '#ffe6ff');
            $('body').css('background-color', '#ffe6ff');
        }
    }
    $("#backcolor_option").change(changeBackColor);



    // Real time search
    function search() {
        var options = {
            valueNames: ['item_name', 'item_amt', 'item_category', 'item_note']
        };
        var docs = new List('search', options);
    }
    $("#search_content").click(search);



    // Helper function to hide the summarylog and display the dashlog
    function showdash() {
        // Display the itemlog
        $(".dashlog").show();
        displayItem();
        // Hide the summarylogs
        $(".summarylog").hide();
    }



    // Bind the button and open the modal
    $("#register_btn").on('click', function() {
        $("#registerModal").modal();
    });

    $("#login_btn").on('click', function() {
        $("#loginModal").modal();
    });

    $("#addtransaction").on('click', function() {
        $("#addExpenseModal").modal();
        showdash();
    });

    $("#income_btn").on('click', function() {
        $("#addIncomeModal").modal();
        showdash();
    });

    $("#debt_btn").on('click', function() {
        $("#addDebtModal").modal();
        showdash();
    });

    $("#credit_btn").on('click', function() {
        $("#addCreditModal").modal();
        showdash();
    });


    $("#dash_btn").on('click', function() {
        // Display the itemlog
        $(".dashlog").show();
        // Hide the summarylogs
        $(".summarylog").hide();
    });



