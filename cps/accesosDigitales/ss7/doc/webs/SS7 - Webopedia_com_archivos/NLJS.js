
v_path = location.pathname;
v_docn = v_path.slice(v_path.lastIndexOf("/"),v_path.length);
if((v_docn == "/")||(v_docn.indexOf("/index") != -1)){v_docn = "/index"};
v_path = v_path.slice(0,v_path.lastIndexOf("/"))

OAS_url = 'http://mjxads.internet.com/RealMedia/ads/';
OAS_sitepage = 'intm/it/www.webopedia.com' + v_path + v_docn;
//OAS_sitepage = 'intm/it/www.webopedia.com';
OAS_listpos = '468x60-1,468x60-2,125x125-1,336x280,125x800,cp1,cp2,cp3,cp4,cp5,cp6,cp7,cp8,cp9,cp10';
OAS_query = '';
OAS_target = '_top';
OAS_version = 10;
OAS_rn = '001234567890'; OAS_rns = '1234567890';
OAS_rn = new String (Math.random()); OAS_rns = OAS_rn.substring (2, 11);
function OAS_NORMAL(pos) {
var_a=('<A HREF="' + OAS_url + 'click_nx.cgi/' + OAS_sitepage );
var_b=(var_a + '/1' + OAS_rns + '@' + OAS_listpos + '!' + pos + '?' );
var_c=(var_b+ OAS_query + '" TARGET=' + OAS_target + '>');
document.write(var_c);
var_aa=('<IMG SRC="' + OAS_url + 'adstream_nx.cgi/' + OAS_sitepage ); 
var_bb=( var_aa + '/1' + OAS_rns + '@' + OAS_listpos + '!' + pos + '?');
var_cc=( var_bb + OAS_query + '" BORDER=0></A>');
document.write(var_cc);
}

OAS_version = 11;
if (navigator.userAgent.indexOf('Mozilla/3') != -1 )
OAS_version = 10;
if (navigator.userAgent.indexOf('Mozilla/4.0 WebTV') != -1)
OAS_version = 10;
if (OAS_version >= 11) {
var_aaa=('<SCRIPT LANGUAGE=JavaScript1.1 SRC="' + OAS_url); 
var_bbb=(var_aaa + 'adstream_mjx.ads/' + OAS_sitepage + '/1'); 
var_ccc=(var_bbb + OAS_rns + '@' + OAS_listpos + '?' + OAS_query );
var_ddd=(var_ccc + '"><\/SCRIPT>');
document.write(var_ddd);
}

document.write('');
function OAS_AD(pos) {
if (OAS_version >= 11)
OAS_RICH(pos);
else
OAS_NORMAL(pos);
}
