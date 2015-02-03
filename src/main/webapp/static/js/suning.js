//jquery插件把表单序列化成json格式的数据start 
(function($){
    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray();
        var str=this.serialize();
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);

var rootURI="/";
var Suning = function () {
	var oTable;
	var selected = [];
	var imgs = [];
	var proSort = false,netSort = false,disSort = false,sortType = 'promotionPrice',sort=false;
	var handleTable = function () {				
		var table=$('#suning_table');
		 oTable = table.dataTable({
			"lengthChange":false,
        	"filter":true,
        	"sort":false,
        	"info":true,
        	"processing":true,
        	"scrollX":"100%",
           	"scrollXInner":"100%",
            "displayLength": 10,
            "dom": "t<'row'<'col-md-6'i><'col-md-6'p>>",
            "columnDefs": [
                {                	
                	'targets':-1,
                	'data':null,//定义列名
                	'render':function(data,type,row){
                    	return '<div class="actions"><a  class="btn btn-sm dark" data-toggle="modal" href="#view" id="product_details">View</a></div>';
                    },
                    'class':'center'
                }
            ],
            "columns": [
               { title: "Category", data: "productCatagory"},
	           
	           { title: "Name",   data: "productName" },
	           { title: "NetPrice",  'render':function(data,type,row){
	        	   	return "¥"+row.netPrice;
	           } },
	           { title: "PromotionPrice",  'render':function(data,type,row){
	        	   	return "¥"+row.promotionPrice;
	           } },
	           { title: "Vendor",    data: "vendorName" },
	           { title: "PartNumber",   data: "partNumber"  },
	           { title: "Url", 'render':function(data,type,row){
	        	   
                   return '<div class="actions"><a  class="btn btn-sm dark" data-toggle="modal"  href="'+row.productLink+'"  target="_blank">OPEN</a></div>';
               }},
	          
	           { title: "Action" ,"class":"center"}
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"/product/suning_list?rand="+Math.random(),
	        "fnDrawCallback":function(oSetting){
	        	selected=[];
	        },
	        "fnServerParams": function ( aoData ) {
		           aoData.push( { "name": "sSort", "value": sort },{ "name": "sSortType", "value": sortType });
			}
		});		
		 
		table.on('click', '#product_details',function(){
			   imgs = [];
			   var data = oTable.api().row($(this).parents('tr')).data();
			   $('#view').find('.name').text(data.productName);
	           $('#view').find('.price').find('strong').text("¥"+data.promotionPrice);
	           $('#view').find('.price').find('span').text("¥"+data.netPrice);
	           var descriptions = data.productParam.split("#*#");
	           var description = [];
	           var html = '<tbody>';
	           $.each(descriptions,function(key,value){
	        	   description = value.split("：");
	        	   html += '<tr><td>'+description[0]+'</td><td>'+description[1]+'</td></tr>'
	           });
	           html +='</tbody>'
	           $('#Description').html(html);
	           $('#des_p').text(data.productParam);
	           $('#view').find('.product-page-options:eq(0)').find('span').text(data.vendorName);
	           $('#view').find('.product-page-options:eq(1)').find('.pull-left:eq(0)').find('span').text(data.partNumber);
	           $('#view').find('.product-page-options:eq(1)').find('.pull-left:eq(1)').find('span').text(data.productCatagory);
	           
	           imgs=data.productImage.substring(0,data.productImage.length-1).split("#");
	           var str = '<ul class="aa">';
	           $.each(imgs,function(i,value){
	        	   str+='<li class="aa"><a target="_blank"><img src="'+value+'"></a></li>'
	           });
	           str+='</ul>';
	           $('#view').find('.slider').empty();
	           $('#view .slider').append(str);
	           $(".slider").yxMobileSlider({
		   			width : 340,
		   			height : 450,
		   			during : 3000
		   		   });
	          handleZClip();
	          $(window).trigger("resize");
        });
		
		
	    
		
		$('#category-one').on('change',function(){
			var jsonData=$('#searchForm').serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);			
			oTable.fnFilter(jsonDataStr);
			var category = [];
			$.ajax({
				"url": rootURI+"/product/suning_category?rand="+Math.random(),
				"type": 'POST',
				"data":{key1:$(this).val(),
					    flag:1
					    },
				"success": function (data,status) { 
					if(status == "success"){
						var obj = JSON.parse(data);
						category= obj.category;
						var html = '<option value="" selected="selected">所有</option>';
						$.each(category,function(key,value) {
							if(!(value == '所有')){
								html +='<option value="'+value+'">'+value+'</option>';
							}
							
				         });
						$('#category-two').empty();
						$('#category-two').append(html);
						$('#category-three').empty();
						$('#category-three').append('<option value="" selected="selected">所有</option>');
						$('.bs-select').selectpicker('refresh');
					}
				},
				"error":function(XMLHttpRequest, textStatus, errorThrown){
	            	 alert(errorThrown);
	             }
				
			})
	    });
		$('#category-two').on('change',function(){
			var jsonData=$('#searchForm').serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);			
			oTable.fnFilter(jsonDataStr);
			var category = [];
			$.ajax({
				"url": rootURI+"/product/suning_category?rand="+Math.random(),
				"type": 'POST',
				 
				"data":{
					    key1:$('#category-one').val(),
					    key2:$(this).val(),
					    flag:2
					    },
				"success": function (data,status) { 
					if(status == "success"){
						var obj = JSON.parse(data);
						category= obj.category;
						var html = '<option value="" selected="selected">所有</option>';
						$.each(category,function(key,value) {
							if(!(value =='所有')){
								html +='<option value="'+value+'">'+value+'</option>';
							}
				         });
						$('#category-three').empty();
						$('#category-three').append(html);
						$('.bs-select').selectpicker('refresh');
					}
				},
				"error":function(XMLHttpRequest, textStatus, errorThrown){
	            	 alert(errorThrown);
	             }
				
			})
	    });
		
		$('#category-three').on('change',function(){
			var jsonData=$('#searchForm').serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);			
			oTable.fnFilter(jsonDataStr);
		});
		
		$('input:radio[name=vendorName]').on('change',function(){
			var jsonData=$('#searchForm').serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);			
			oTable.fnFilter(jsonDataStr);
		});
			
		
		
		/* handle show/hide columns*/
        var tableColumnToggler = $('#column_toggler');		
		$('input[type="checkbox"]', tableColumnToggler).change(function () {
		    /* Get the DataTables object again - this is not a recreation, just a get of the object */
		    var iCol = parseInt($(this).attr("data-column"));
		    var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
		    oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
        
		$('#download_images').on('click',function(){
			Downer(imgs);
		});
        
	};
	
	 var handleBootstrapSelect = function() {
	        $('.bs-select').selectpicker({
	            iconBase: 'fa',
	            tickIcon: 'fa-check'
	        });
	    }
	 
	 var searchSubmit = function(){
		
				var jsonData=$('#searchForm').serializeJson();
				var jsonDataStr=JSON.stringify(jsonData);			
				oTable.fnFilter(jsonDataStr);
				return false;
			
	 }
	 
	 var searchValidation = function() {
	        var form = $('#searchForm');
	        var errorDiv = $('.alert-danger', form);            
	        form.validate({
	            errorElement: 'span', //default input error message container
	            errorPlacement: function(error, element) {
	            	error.appendTo(element.parent().parent());  
	            },
	            errorClass: 'help-block help-block-error', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",  // validate all fields including form hidden input                
	            rules: {
	            	price_from:{
	            		number:true
	            		
	            	},
	            	price_to:{
	            		number:true,
	            	},
	            	disprice_from:{
	            		number:true
	            		
	            	},
	            	disprice_to:{
	            		number:true,
	            	}
	            },
	           invalidHandler: function (event, validator) { //display error alert on form submit              
	                errorDiv.show();                    
	            },

	                highlight: function (element) { // hightlight error inputs
	                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
	                },

	                unhighlight: function (element) { // revert the change done by hightlight
	                    $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
	                },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error'); // set success class to the control group
	            },
	            onfocusout:function(element){
	            	$(element).valid();
	            },
	            submitHandler: function (form) { 
	            	errorDiv.hide();
	            	searchSubmit();
	            }
	        });
	    };
	
	 
	//提示信息处理方法（是在页面中指定位置显示提示信息的方式）
	var handleAlerts = function(msg,msgType,position) {         
        Metronic.alert({
            container: position, // alerts parent container(by default placed after the page breadcrumbs)
            place: "prepent", // append or prepent in container 
            type: msgType,  // alert's type (success, danger, warning, info)
            message: msg,  // alert's message
            close: true, // make alert closable
            reset: true, // close all previouse alerts first
            focus: false, // auto scroll to the alert after shown
            closeInSeconds: 10, // auto close after defined seconds, 0 never close
            icon: "warning" // put icon before the message, use the font Awesone icon (fa-[*])
        });        

    };
    
var  handleZClip = function(){
		
		$("#copy_d").delay(250).queue(function(next){
	        $(this).zclip({
	        	path: rootURI+'/assets/global/plugins/zclip/ZeroClipboard.swf',
				copy:  function(){
					   var descriptions = $('#des_p').text().split("#*#");
					   var description = [];
					   var html = '<table style="width: 602px; height: 350px; text-align: center;" border="0" cellspacing="0" cellpadding="0"><tbody>';
			           $.each(descriptions,function(key,value){
			        	   description = value.split("：");
			        	   if(key % 2 ==0){
			        		   html += '<tr style="background-color: #ebebeb;">'+
			        		           '<td style="height: 35px; border: 1px solid #ebebeb;"><p><span style="font-size: small; color: #262626;">'+description[0]+'</p></span></td>'+
			        		           '<td style="height: 35px; border: 1px solid #ebebeb;"><p><span style="font-size: small; color: #262626;">'+description[1]+'</p></span></td>'+
			        		           '</tr>';
			        	   }else{
			        		   html += '<tr>'+
	        		           '<td style="height: 35px; border: 1px solid #ebebeb;"><p><span style="font-size: small; color: #262626;">'+description[0]+'</p></span></td>'+
	        		           '<td style="height: 35px; border: 1px solid #ebebeb;"><p><span style="font-size: small; color: #262626;">'+description[1]+'</p></span></td>'+
	        		           '</tr>';
			        	   }
			        	   
			           });
			           html +='</tbody></table>';
			           return html;
				},
				afterCopy: function(){
				   $('#msg').remove();
				   $("<span id='msg'/>").insertAfter($('#copy_d')).text('复制成功').fadeOut(2000);
				}
	        });
	        next();
	    });
		$("#copy_n").delay(250).queue(function(next){
			$(this).zclip({
	        	path: rootURI+'/assets/global/plugins/zclip/ZeroClipboard.swf',
				copy: $('.name').text(),
				afterCopy: function(){
				   $('#msg').remove();
				   $("<span id='msg'/>").insertAfter($('#copy_n')).text('复制成功').fadeOut(2000);
				}
	        });
	        next();
	    });
		$("#copy_b").delay(250).queue(function(next){
	        $(this).zclip({
	        	path: rootURI+'/assets/global/plugins/zclip/ZeroClipboard.swf',
				copy: $('#view').find('.product-page-options:eq(0)').find('span').text(),
				afterCopy: function(){
				   $('#msg').remove();
				   $("<span id='msg'/>").insertAfter($('#copy_b')).text('复制成功').fadeOut(2000);
				}
	        });
	        next();
	    });
		
	}

 var Downer = (function(files){
	var h5Down = !/Trident|MSIE/.test(navigator.userAgent);
	// try{
	// 	h5Down = document.createElement("a").hasOwnProperty("download");
	// } catch(e){
	// 	h5Down = document.createElement("a").download;
	// }

	/**
	 * 在支持 download 属性的情况下使用该方法进行单个文件下载
	 * 目前 FF 还不支持 download 属性，所以 FF 必须另觅他法！
	 * @param  {String} fileName
	 * @param  {String|FileObject} contentOrPath
	 * @return {Null}
	 */
	function downloadFile(fileName, contentOrPath){
		var aLink = document.createElement("a"),
			evt = document.createEvent("HTMLEvents"),
			isData = contentOrPath.slice(0, 5) === "data:",
			isPath = contentOrPath.lastIndexOf(".") > -1;

		// 初始化点击事件
		// 注：initEvent 不加后两个参数在FF下会报错
		evt.initEvent("click",false,false);

		// 添加文件下载名
		aLink.download =fileName;

		// 如果是 path 或者 dataURL 直接赋值
		// 如果是 file 或者其他内容，使用 Blob 转换
		aLink.href = isPath || isData ? contentOrPath
					: URL.createObjectURL(new Blob([contentOrPath]));

		aLink.dispatchEvent(evt);
	}

	/**
	 * [IEdownloadFile description]
	 * @param  {String} fileName
	 * @param  {String|FileObject} contentOrPath
	 */
	function IEdownloadFile(fileName, contentOrPath, bool){
		var isImg = contentOrPath.slice(0, 10) === "data:image",
			ifr = document.createElement('iframe');

		ifr.style.display = 'none';
		ifr.src = contentOrPath;

		document.body.appendChild(ifr);

		// dataURL 的情况
		isImg && ifr.contentWindow.document.write("<img src='" + 
				contentOrPath + "' />");

		// 保存页面 -> 保存文件
		// alert(ifr.contentWindow.document.body.innerHTML)
		if(bool){
			ifr.contentWindow.document.execCommand('SaveAs', false, fileName);
			document.body.removeChild(ifr);
		} else {
			setTimeout(function(){
				ifr.contentWindow.document.execCommand('SaveAs', false, fileName);
				document.body.removeChild(ifr);
			}, 0);
		}
	}

	/**
	 * [parseURL description]
	 * @param  {String} str [description]
	 * @return {String}     [description]
	 */
	function parseURL(str){
		return str.lastIndexOf("/") > -1 ? str.slice(str.lastIndexOf("/") + 1) : str;
	}

	return function(files){
		// 选择下载函数
		var downer = h5Down ? downloadFile : IEdownloadFile;

		// 判断类型，处理下载文件名
		if(files instanceof Array) {
			for(var i = 0, l = files.length; i < l ; i++) 
				// bug 处理
				downer(parseURL(files[i]), files[i], true);
		} else if(typeof files === "string") {
			downer(parseURL(files), files);
		} else {
			// 对象
			for(var file in files) downer(file, files[file]);
		}
	}

})(); 
    
 var priceSort = function(){
		
		$('#proprice_sort').on('click',function(){
			if(!proSort){
				$(this).find('i').removeClass('fa-arrow-down');
				$(this).find('i').addClass('fa-arrow-up');
				proSort=true;
			}else{
				$(this).find('i').removeClass('fa-arrow-up');
				$(this).find('i').addClass('fa-arrow-down');
				proSort=false;
			}
			sortType = 'promotionPrice';
			sort=proSort;
			oTable.fnFilter();
		});
		$('#netprice_sort').on('click',function(){
			if(!netSort){
				$(this).find('i').removeClass('fa-arrow-down');
				$(this).find('i').addClass('fa-arrow-up');
				netSort=true;
			}else{
				$(this).find('i').removeClass('fa-arrow-up');
				$(this).find('i').addClass('fa-arrow-down');
				netSort=false;
			}
			sortType = 'netPrice';
			sort=netSort;
			oTable.fnFilter();
		});
		$('#disprice_sort').on('click',function(){
			if(!disSort){
				$(this).find('i').removeClass('fa-arrow-down');
				$(this).find('i').addClass('fa-arrow-up');
				disSort=true;
			}else{
				$(this).find('i').removeClass('fa-arrow-up');
				$(this).find('i').addClass('fa-arrow-down');
				disSort=false;
			}
			sortType = 'disPrice';
			sort=disSort;
			oTable.fnFilter();
		});
		
	}
 
    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	handleTable();  
        	handleBootstrapSelect(); 
        	searchValidation();
        	priceSort();
        }

    };

}();
