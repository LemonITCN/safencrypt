/**
 * AES加密工具类
 * @constructor
 */
function SAES() {
}

/**
 * 加密数据
 * @param {type} data 待加密的字符串
 * @param {type} keyStr 秘钥
 * @param {type} ivStr 向量
 * @returns  {string}加密后的数据
 */
SAES.aesEncrypt = function (data, keyStr, ivStr) {
    var sendData = CryptoJS.enc.Utf8.parse(data);
    var key = CryptoJS.enc.Utf8.parse(keyStr);
    var iv = CryptoJS.enc.Utf8.parse(ivStr);
    var encrypted = CryptoJS.AES.encrypt(sendData, key, {
        iv: iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Iso10126
    });
    //return CryptoJS.enc.Base64.stringify(encrypted.toString(CryptoJS.enc.Utf8));
    return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
};

/**
 * AES加密
 * @param data
 * @param keyStr
 * @return {string}加密后的数据
 */
SAES.encrypt = function (data, keyStr) {
    return this.aesEncrypt(data, keyStr, keyStr);
};

/**
 *
 * @param {type} data BASE64的数据
 * @param {type} key 解密秘钥
 * @param {type} iv 向量
 * @return {string} 解密后的数据
 */
SAES.aesDecrypt = function (data, keyStr, ivStr) {
    var key = CryptoJS.enc.Utf8.parse(keyStr);
    var iv = CryptoJS.enc.Utf8.parse(ivStr);
    //解密的是基于BASE64的数据，此处data是BASE64数据
    var decrypted = CryptoJS.AES.decrypt(data, key, {iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Iso10126});
    return decrypted.toString(CryptoJS.enc.Utf8);
};

/**
 * AES解密
 * @param data
 * @param keyStr
 * @return {string} 解密后的数据
 */
SAES.decrypt = function (data, keyStr) {
    return this.aesDecrypt(data, keyStr, keyStr);
};

