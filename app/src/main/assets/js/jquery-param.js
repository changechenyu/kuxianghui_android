function getRequestParams(){
	var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
      hash = hashes[i].split('=');
      vars.push(hash[0]);
      vars[hash[0]] = hash[1];
    }
    return vars;
}

/**
 * 获取路径中指定参数的值
 * @param name
 * @returns
 */
function getRequestParam(name){
	 return getRequestParams()[name];
}

/**
 * 获取项目名称用于装填请求路径
 * @returns
 */
function getProjectPath(){
	return "http://10.68.5.30";
	//return "http://tcl.aebiz.com:8080/tcldistribution";
}
