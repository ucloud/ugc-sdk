package logic

type WordCountBean struct {
	word  string
	count int
}

func NewWordCountBean(word string, count int) *WordCountBean {
	return &WordCountBean{word, count}
}

type WordCountBeanList []*WordCountBean

func (list WordCountBeanList) Len() int {
	return len(list)
}

func (list WordCountBeanList) Less(i, j int) bool {
	if list[i].count > list[j].count {
		return true
	} else if list[i].count < list[j].count {
		return false
	} else {
		return list[i].word < list[j].word
	}
}

func (list WordCountBeanList) Swap(i, j int) {
	var temp *WordCountBean = list[i]
	list[i] = list[j]
	list[j] = temp
}

func (list WordCountBeanList) totalCount() int {
	totalCount := 0
	for _, v := range list {
		totalCount += v.count
	}

	return totalCount
}
