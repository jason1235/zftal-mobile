var NewsList = {
		
		page : {
			start : 1,
			size : 30
		},
		
		// 分页数据模板 list
		appendTemplate : [
'{{#this}}',
'{{#each content.main}}',
'<li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">',
'<div class="am-u-sm-4 am-list-thumb">',
'  <a href="{{link}}" class="">',
'    <img src="{{img}}" alt=""/>',
'  </a>',
'</div>',
'<div class=" am-u-sm-8 am-list-main">',
'    <h3 class="am-list-item-hd"><a href="{{link}}" class="">{{title}}</a></h3>',
'    <div class="am-list-item-text">{{desc}}</div>',

'</div>',
'</li>',
'{{/each}}',
'{{/this}}'
		                  ],
		    appendTemplate2:[
'{{#this}}',
'{{#each content}}',
'<li>',
'<div class="am-gallery-item">',
'    <a href="{{img}}" class="">',
'      <img src="{{img}}"  alt="{{title}}"/>',
'        <h3 class="am-gallery-title">{{title}}</h3>',
'        <div class="am-gallery-desc">{{date}}</div>',
'    </a>',
'</div>',
'</li>',
'{{/each}}',
'{{/this}}'
],              
		                  
		// 获取解析数据
		fetchList : function(data, list, url,param) {
			if (list) {
				this.parseData(list,data);
				return;
			}
			if (url) {
				if (param) {
					if (url.indexOf('?') > -1) {
						url = url + "&" + param;
					} else {
						url = url + "?" + param;
					}
					
				}
				
				var that = this;
				$.get(url, function(result){
					var ret = eval("(" + result + ")");
					that.parseData(ret.news,data);
				});
			}
			
		},
		
		parseData : function(newsList,data) {
			var mainData = [];
		  	if (newsList instanceof Array) {
		  		for (var i = 0,len = newsList.length; i < len; i++) {
		  			var obj = {};
		  			obj.title = newsList[i].title;
		  			//TODO: 链接需要修改，现在只是传入ID
		  			obj.link = _path + "/mobile/web_content.html?newsId=" + newsList[i].id;
		  			if (newsList[i].logopath) {
		  				if (/[(http)||(https)]:.*/i.test(newsList[i].logopath)) {
		  					obj.img = newsList[i].logopath;
		  				} else {
		  				obj.img = _path + "/upload/" + newsList[i].logopath;
		  				}
		  				
		  			} else {
		  				obj.img = _path + "/assets/i/avatar.jpg";
		  			}
		  			obj.date = newsList[i].time;
		  			mainData.push(obj);
		  		}
		  	} else {
		  		var obj = {};
		  		if (newsList) {
		  		var news = newsList;
		  			obj.title = news.title;
		  		obj.link = _path + "/mobile/web_content.html?newsId=" + news.id;
		  		if (news.logopath) {
		  			if (/[(http)||(https)]:.*/i.test(news.logopath)) {
	  					obj.img = news.logopath;
	  				} else {
	  					obj.img = _path + "/upload/" + news.logopath;
	  				}
		  			
		  		} else {
		  			obj.img = _path + "/assets/i/avatar.jpg";
		  		}
		  		obj.date = news.time;
		  		mainData.push(obj);
		  		}
		  	}
		  	var options = {type:"thumb"};
		  	if ('${listType}' == '2') {
		  		options.thumbPosition = "top";
		  	} else {
		  		options.thumbPosition = "left";
		  	}
		  	data.list_news.options = options;
		  	data.list_news.content.main = mainData;
			
		},
		
		// 渲染模板
		render : function(data, list, container,config) {
			var source = "{{>list_news list_news}}";
			if (config) {
				if (config.type=="gallery") {
					source = "{{>gallery gallery}}";
				}
				if (config.callback) {
					config.callback(list, data);
				} else {
					this.fetchList(data, list);
				}
			} else {
				this.fetchList(data, list);
			}
			
			var template = Handlebars.compile(source);
			
			var html = template(data);
			$(container).html(html)
		},
		
		// 加载分页数据
		loadMore : function(data,container, url,config) {
			this.page.start++;
			var source = this.appendTemplate.join("");
			if (config) {
				if (config.type=="gallery") {
					source = this.appendTemplate2.join("");
				}
				if (config.callback) {
					console.log("loadMore");
					config.callback();
				}
				
				
			}
			this.fetchList(data, null, url, "start=" + this.page.start + "&size=" + this.page.size);
			
			var template = Handlebars.compile(source);
			$(container).append(template(data));
		}
}