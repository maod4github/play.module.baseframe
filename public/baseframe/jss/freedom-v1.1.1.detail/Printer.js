function Printer() {
}

Printer.toggle = {
	info : true,
	log : true,
	warn : true,
	err : true,
	obj : true
};

Printer.enableAll = function () {
	Printer.toggle.info = true;
	Printer.toggle.log = true;
	Printer.toggle.warn = true;
	Printer.toggle.err = true;
	Printer.toggle.obj = true;
	return this;
};

Printer.disableAll = function () {
	Printer.toggle.info = false;
	Printer.toggle.log = false;
	Printer.toggle.warn = false;
	Printer.toggle.err = false;
	Printer.toggle.obj = false;
	return this;
};

Printer.info = function(msg) {
	if (!Printer.toggle.info) {
		return ;
	}
	try {
		console.info(msg);
	} 
	catch (e) {
	}
};

Printer.warn = function (msg) {
	if (!Printer.toggle.warn) {
		return ;
	}
	try {
		console.warn(msg);
	} 
	catch (e) {
	}
};

Printer.log = function(msg, b) {
	if (!Printer.toggle.log) {
		return ;
	}
	try {
		console.log(msg, b == null ? '' : b);
	} 
	catch (e) {
	}
};

Printer.err = function(msg) {
	if (!Printer.toggle.err) {
		return ;
	}
	try {
		console.error(msg);
	} 
	catch (e) {
	}
};

Printer.obj = function(obj) {
	if (!Printer.toggle.obj) {
		return ;
	}
	try {
		console.info(obj);
	} 
	catch (e) {
	}
};
