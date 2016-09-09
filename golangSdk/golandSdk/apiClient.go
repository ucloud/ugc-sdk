package ugcapi

import (
	"crypto/sha1"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"net/url"
	"sort"
	"strings"
)

func Vefsig(private_key string, params map[string]string) string {
	var keys []string
	for k := range params {
		keys = append(keys, k)
	}
	sort.Strings(keys)

	var result string

	for _, k := range keys {
		result = result + k + params[k]
	}
	result = result + private_key

	t := sha1.New()
	io.WriteString(t, result)
	sha1 := string(t.Sum(nil)[:])
	sha1String := fmt.Sprintf("%x", sha1)
	return sha1String
}

func GenerateUrl(baseUrl string, paramMap map[string]string) string {
	url_request := baseUrl

	var is_first bool = true

	for k, v := range paramMap {
		var encode_param string
		encode_param = url.QueryEscape(v)
		if is_first {
			is_first = false
		} else {
			url_request += "&"
		}
		url_request += k + "=" + encode_param
	}
	return url_request
}

func HttpGet(url string) (res string, err error) {
	resp, err := http.Get(url)
	if err != nil {
		return
	}
	defer resp.Body.Close()

	body, _ := ioutil.ReadAll(resp.Body)
	return string(body), nil
}

func HttpPost(url string, data string) (rsp string, err error) {
	resp, err := http.Post(url, "application/octet-stream", strings.NewReader(data))
	if err != nil {
		return
	}

	defer resp.Body.Close()
	body, _ := ioutil.ReadAll(resp.Body)
	return string(body), nil
}
