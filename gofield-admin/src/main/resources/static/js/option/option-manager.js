class OptionManager {
    data;
    initData;

    constructor() {
        // 예시 데이터 구조
        this.data = this.getEmptyData();
    }

    getEmptyData() {
        return {
            isOption: false,
            optionType: 'COMBINATION',
            optionGroupList: [
                // {
                //     id: 1,
                //     name: '색상',
                //     value: '빨강,파랑,블랙',
                // },
                // ...
            ],
            optionGroupDeleteIds: [],
            optionItemListDeleteItemNumbers: [],
            optionItemList: [
                // {
                //     itemNumber: '',
                //     values: ['빨강', 's'],
                //     price: 50000,
                //     qty: 30,
                //     status: 'SALE',
                // },
                // ...
            ],
        };
    }

    isSameArray(values1, values2, isLengthMatched) {
        if (isLengthMatched === true) {
            if (values1.length !== values2.length) {
                return false;
            }
        }
        return values1.every(values1Item => values2.includes(values1Item));
    }

    tryInit() {
        const jsonString = document.getElementById('option-manager-init-data').value;
        if (typeof jsonString !== 'string' || jsonString === '') {
            this.initData = { ...this.data };
            this.render();
            return;
        }

        try {
            const json = JSON.parse(jsonString);
            this.initData = { ...json };
            this.data = { ...json };
            this.render();
        } catch (e) {
            this.initData = this.getEmptyData();
            this.data = this.getEmptyData();
            console.error('e', e);
            this.render();
        }
    }

    getDeletedOptionGroupIds() {
        const currentDisplayedOptionGroupIds = this.data.optionGroupList.map(y => y.id);
        console.log('@currentDisplayedOptionGroupIds', currentDisplayedOptionGroupIds);

        return this.initData.optionGroupList.filter(x => {
            console.log(`currentDisplayedOptionGroupIds.includes(${x.id})`);
            return !currentDisplayedOptionGroupIds.includes(x.id);
        }).map(x => x.id);
    }

    getDeletedOptionItemListItemNumbers() {
        return this.initData.optionItemList.filter(x => !this.data.optionItemList.map(y => y.itemNumber).includes(x.itemNumber)).map(x => x.itemNumber);
    }

    isOptionGroupChanged() {
        return JSON.stringify(this.initData.optionGroupList) !== JSON.stringify(this.data.optionGroupList);
    }

    isOptionListChanged() {
        return JSON.stringify(this.initData.optionItemList) !== JSON.stringify(this.data.optionItemList);
    }

    checkSubmit(form) {
        try {
            if (this.isOptionGroupChanged()) {
                if (!this.isOptionListChanged()) {
                    alert('옵션입력 부분의 변경이 감지되었습니다. 옵션 목록으로 적용 버튼 클릭 후 옵션가, 재고수량, 판매상태 정보 입력 후 다시 시도해주세요.');
                    return false;
                }
            }

            if (this.data.isOption) {
                if (this.data.optionGroupList.length === 0 || this.data.optionItemList.length === 0) {
                    alert('옵션선택을 "설정함"으로 선택했을 경우 옵션입력 부분과 옵션 목록 부분이 입력되어 있어야합니다.');
                    return false;
                }
            }

            const target = document.querySelector("input[name=optionInfo]");
            const value = JSON.stringify(optionManager.getData());
            // console.log('optionManager.getData()', optionManager.getData());
            target.value = value;
            target.setAttribute("value", value);
            return true;
        } catch (e) {
            console.error('error =>', e);
            return false;
        }
    }

    changeOptionFlag(target) {
        // console.log('target', target);
        const value = target.value;
        if (typeof value !== 'string') {
            return;
        }
        this.data.isOption = value === "true";
        if (this.data.isOption) {
            document.getElementById('option-detail-control-form-area').style.display = 'block';
        } else {
            document.getElementById('option-detail-control-form-area').style.display = 'none';
        }
    }

    changeOptionComposeType(target) {
        const value = target.value; // single, multiple
        if (typeof value !== 'string') {
            return;
        }
        if (!confirm('옵션 구성 타입을 바꾸면 옵션 입력 부분과 옵션 목록 부분이 빈 값으로 초기화 됩니다. 계속 진행하시겠습니까?')) {
            document.querySelector(`#option_compose_type_${value === "SIMPLE" ? "COMBINATION" : "SIMPLE"}`).checked = true;
            return;
        }
        this.data.optionType = value;
        this.data.optionGroupList = [];
        this.data.optionItemList = [];
        this.render();
    }

    deleteOptionItem(index) {
        if (!confirm('해당 옵션 아이템을 삭제하시겠습니까?')) {
            return;
        }
        const copyOptionGroupList = [ ...this.data.optionGroupList ];
        copyOptionGroupList.splice(index, 1);
        this.data.optionGroupList = copyOptionGroupList;
        this.render();
    }

    addOptionItem() {
        if (this.data.optionType === "SIMPLE") {
            if (this.data.optionGroupList.length >= 1) {
                alert('단독형인 경우에 옵션은 1개까지만 등록 가능합니다. 옵션을 여러개 등록하기 위해선 조합형을 선택해주세요.');
                return;
            }
        }

        const newArray = [ ...this.data.optionGroupList ].concat({
            id: 0,
            name: '',
            value: '',
        });
        this.data.optionGroupList = newArray;
        this.render();
    }

    changeOptionItemName(index, target) {
        this.data.optionGroupList[index].name = target.value;
    }

    changeOptionItemValue(index, target) {
        this.data.optionGroupList[index].value = target.value;
    }

    applyOptionList() {
        if (this.data.optionGroupList.find(x => x.name.trim() === '') !== undefined) {
            alert('옵션 그룹명이 공백인 것이 존재합니다. 옵션 그룹명을 확인해주세요.');
            return;
        }

        if (this.data.optionGroupList.find(x => x.value.trim() === '') !== undefined) {
            alert('옵션 그룹 값이 공백인 것이 존재합니다. 옵션 그룹 값을 확인해주세요.');
            return;
        }

        if (!confirm('옵션 목록으로 적용하면 옵션 목록이 전부 초기화 됩니다. 계속 진행하시겠습니까?')) {
            return;
        }
        const all_list_option = d3.cross(...this.data.optionGroupList.map(item => item.value.split(',')));
        // console.log('all_list_option', all_list_option);
        this.data.optionItemList = all_list_option.map((item) => {
            // const targetItem = this.getInitDataMatchedOptionItem(item);
            return {
                itemNumber: this.getItemNumber(item),
                values: item,
                // price: targetItem !== undefined ? targetItem.price : 0,
                price: 0,
                // qty: targetItem !== undefined ? targetItem.qty : 0,
                qty: 0,
                // status: targetItem !== undefined ? targetItem.status : 'SALE',
                status: 'SALE'
            };
        });
        // this.data.option_list =
        this.render();
    }

    getInitDataMatchedOptionItem(optionNames, isLengthMatched) {
        const target = this.initData.optionItemList.find(x => {
            return this.isSameArray(x.values, optionNames, isLengthMatched);
        });
        return target;
    }

    getItemNumber(values) {
        if (!Array.isArray((values))) {
            return '';
        }

        if (values.length === 0) {
            return '';
        }

        const target = this.getInitDataMatchedOptionItem(values, true);
        if (target === undefined) {
            return '';
        }

        return target.itemNumber;
    }

    changeOptionListRowPrice(index, target) {
        const value = target.value;
        if (typeof value !== 'string') {
            return;
        }
        this.data.optionItemList[index].price = value;
    }

    changeOptionListRowInventoryCount(index, target) {
        const value = target.value;
        if (typeof value !== 'string') {
            return;
        }
        this.data.optionItemList[index].qty = value;
    }

    changeOptionListRowStatus(index, target) {
        const value = target.value;
        if (typeof value !== 'string') {
            return;
        }
        this.data.optionItemList[index].status = value;
    }

    changeOptionListAllCheck(target) {
        const checked = target.checked;
        document.querySelectorAll("tr.option-list-item-row").forEach((element) => {
             element.querySelector("input[name=option-list-item-check]").checked = checked;
        });
    }

    deleteSelectedOptionList() {
        if (!confirm('선택된 옵션 리스트를 삭제하시겠습니까?')) {
            return;
        }

        const deleteTargetIndexes = [];
        document.querySelectorAll("tr.option-list-item-row").forEach((element) => {
            const index = Number(element.getAttribute("data-index"));
            if (element.querySelector("input[name=option-list-item-check]").checked) {
                deleteTargetIndexes.push(index);
            }
        });

        if (deleteTargetIndexes.length === 0) {
            alert('선택된 옵션 리스트가 없습니다.');
            return;
        }

        this.data.optionItemList = this.data.optionItemList.filter((x, index) => !deleteTargetIndexes.includes(index));
        this.render();
    }

    changeSelectedOptionListStatus() {
        const target = document.getElementById('selected-status-select');

        const text = target.options[target.selectedIndex].text;
        const value = target.options[target.selectedIndex].value;

        if (!confirm(`선택된 옵션 리스트의 상태를 ${text} 으로 변경하시겠습니까?`)) {
            return;
        }

        const changeTargetIndexes = [];
        document.querySelectorAll("tr.option-list-item-row").forEach((element) => {
            const index = Number(element.getAttribute("data-index"));
            if (element.querySelector("input[name=option-list-item-check]").checked) {
                changeTargetIndexes.push(index);
            }
        });

        if (changeTargetIndexes.length === 0) {
            alert('선택된 옵션 리스트가 없습니다.');
            return;
        }

        this.data.optionItemList.forEach((item, index) => {
            if (changeTargetIndexes.includes(index)) {
                item.status = value;
            }
        });
        this.render();
    }

    getTableHTML(data) {
        const htmlCode = `
            <div class="w-full block" data-origin="option-manager">
                <div class="w-full h-2"></div>
                <div data-name="form-row" class="w-full flex flex-wrap">
                    <div class="flex flex-wrap items-start content-start" style="width: 140px;">
                        선택
                    </div>
                    <div class="flex flex-wrap items-start content-start" style="width: calc(100% - 140px);">
                        <div class="inline-flex items-center items-center mr-2">
                            <input ${data.isOption === true ? "checked " : ""}class="inline-flex" type="radio" name="option_flag" value="true" id="option_flag_on" onchange="optionManager.changeOptionFlag(this)" />
                            <label class="inline-flex font-normal" for="option_flag_on">&nbsp;설정함</label>
                        </div>
                        <div class="inline-flex items-center items-center">
                            <input ${data.isOption === false ? "checked " : ""}class="inline-flex" type="radio" name="option_flag" value="false" id="option_flag_off" onchange="optionManager.changeOptionFlag(this)" />
                            <label class="inline-flex font-normal" for="option_flag_off">&nbsp;설정안함</label>
                        </div>
                    </div>
                </div>
                <div class="w-full" id="option-detail-control-form-area" style="display: ${this.data.isOption ? 'block' : 'none'}">
                    <div class="w-full h-px bg-slate-200 my-2"></div>
                    <div data-name="form-row" class="w-full flex flex-wrap">
                        <div class="flex flex-wrap items-start content-start" style="width: 140px;">
                            옵션 구성타입
                        </div>
                        <div class="flex flex-wrap items-start content-start" style="width: calc(100% - 140px);">
                            <div class="inline-flex items-center items-center mr-2">
                                <input ${data.optionType === "SIMPLE" ? "checked " : ""}class="inline-flex" type="radio" name="option_compose_type" value="SIMPLE" id="option_compose_type_SIMPLE" onchange="optionManager.changeOptionComposeType(this)" />
                                <label class="inline-flex font-normal" for="option_compose_type_SIMPLE">&nbsp;단독형</label>
                            </div>
                            <div class="inline-flex items-center items-center">
                                <input ${data.optionType === "COMBINATION" ? "checked " : ""}class="inline-flex" type="radio" name="option_compose_type" value="COMBINATION" id="option_compose_type_COMBINATION" onchange="optionManager.changeOptionComposeType(this)" />
                                <label class="inline-flex font-normal" for="option_compose_type_COMBINATION">&nbsp;조합형</label>
                            </div>
                        </div>
                    </div>
                    <div class="w-full h-px bg-slate-200 my-2"></div>
                    <div data-name="form-row" class="w-full flex flex-wrap">
                        <div class="flex flex-wrap items-start content-start" style="width: 140px;">
                            옵션 입력
                        </div>
                        <div class="flex flex-wrap items-start content-start" style="width: calc(100% - 140px);">
                            <table class="w-full">
                                <thead>
                                    <tr>
                                        <th style="width: 100px">
                                            옵션명
                                        </th>
                                        <th style="width: 300px">
                                            옵션값
                                        </th>
                                        <th>
                                            &nbsp;
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    ${(function(){
                                        let htmlCode = '';
                                        let index = 0;
                                        for (const item of data.optionGroupList) {
                                            htmlCode += `
                                                <tr class="option-item-row" data-index="${index}" data-option-group-id="${item.id}">
                                                    <td>
                                                        <input name="option_name" type="text" placeholder="예시) 색상" value="${item.name}" onchange="optionManager.changeOptionItemName(${index}, this)" />
                                                    </td>
                                                    <td>
                                                        <input name="option_values" type="text" placeholder="예시) 빨강,파랑,블랙,분홍" style="width: 100%;" value="${item.value}" onchange="optionManager.changeOptionItemValue(${index}, this)" />
                                                    </td>
                                                    <td>
                                                        <span class="text-red-600 cursor-pointer" onclick="optionManager.deleteOptionItem(${index})">삭제</span>
                                                    </td>
                                                </tr>
                                            `;
                                            index++;
                                        }
                                        return htmlCode;   
                                    })()}
                                </tbody>
                            </table>
                            <div class="w-full">
                                <a class="btn btn-info btn-sm mr-2" onclick="optionManager.addOptionItem()">옵션 추가</a>
                                <a class="btn btn-dark btn-sm" onclick="optionManager.applyOptionList()">옵션 목록으로 적용</a>
                            </div>
                        </div>
                    </div>
                    <div class="w-full h-px bg-slate-200 my-2"></div>
                    <div data-name="form-row" class="w-full flex flex-wrap">
                        <div class="flex flex-wrap items-start content-start" style="width: 140px;">
                            옵션 목록
                        </div>
                        <div class="flex flex-wrap items-start content-start" style="width: calc(100% - 140px);">
                            <div class="w-full block">
                                <div class="inline-flex">
                                    선택한 옵션 리스트를
                                </div> 
                                <div class="inline-flex flex-col">
                                    <ul>
                                        <li>
                                            <a class="btn btn-danger btn-sm" onclick="optionManager.deleteSelectedOptionList()">삭제합니다.</a>
                                        </li>
                                        <li>
                                            <select class="select select2-blue" id="selected-status-select">
                                                <option value="SOLD_OUT">품절</option>
                                                <option value="HIDE">숨김</option>
                                                <option value="SALE">판매중</option>
                                            </select>
                                            <a class="btn btn-info btn-sm" onclick="optionManager.changeSelectedOptionListStatus()">으로 변경합니다.</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <table class="w-full table table-bordered table-hover dataTable dtr-inline dt-center table-valign-middle">
                                <thead>
                                    <tr>
                                        <th rowspan="2">
                                            <input type="checkbox" name="option-list-all-check" onchange="optionManager.changeOptionListAllCheck(this)" />
                                        </th>
                                        <th colspan="${data.optionGroupList.length}">
                                            옵션명
                                        </th>
                                        <th rowspan="2">
                                            옵션가
                                        </th>
                                        <th rowspan="2">
                                            재고수량
                                        </th>
                                        <th rowspan="2">
                                            판매상태
                                        </th>
                                    </tr>
                                    <tr>
                                        ${(function(){
                                            let ths = '';
                                            for (const item of data.optionGroupList) {
                                                ths += `
                                                    <th>
                                                        ${item.name}
                                                    </th>
                                                `;
                                            }
                                            return ths;
                                        })()}
                                    </tr>
                                </thead>
                                <tbody>
                                    ${(function(){
                                        let trs = ``;
                                        let index = 0;
                                        for (const item of data.optionItemList) {
                                            trs += `
                                                <tr class="option-list-item-row" data-index="${index}" data-option-item-number="${item.itemNumber}">
                                                    <td>
                                                        <input type="checkbox" name="option-list-item-check" />
                                                    </td>
                                                    ${(function(){
                                                        let tds = ``;
                                                        for (const item2 of item.values) {
                                                            tds += `
                                                                <td>
                                                                    ${item2}
                                                                </td>
                                                            `;
                                                        }
                                                        return tds;
                                                    })()}
                                                    <td>
                                                        <input ${data.optionType === "SIMPLE" ? "disabled " : ""} type="number" name="price" value="${item.price}" onchange="optionManager.changeOptionListRowPrice(${index}, this)" />
                                                    </td>
                                                    <td>
                                                        <input type="number" name="qty" value="${item.qty}" onchange="optionManager.changeOptionListRowInventoryCount(${index}, this)" />
                                                    </td>
                                                    <td>
                                                        <select class="select select2-blue" onchange="optionManager.changeOptionListRowStatus(${index}, this)">
                                                            <option ${item.status === 'SOLD_OUT' ? 'selected ': ''}value="SOLD_OUT">품절</option>
                                                            <option ${item.status === 'HIDE' ? 'selected ': ''}value="HIDE">숨김</option>
                                                            <option ${item.status === 'SALE' ? 'selected ': ''}value="SALE">판매중</option>
                                                        </select>
                                                    </td>
                                                </tr>                                     
                                            `;
                                            index++;
                                        }
                                        return trs;
                                    })()}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        `;
        return htmlCode;
    }

    render() {
        const optionContentBox = document.getElementById('option-content-box');
        // console.log('optionContentBox', optionContentBox);
        if (optionContentBox === null) return;
        for (let i = 0; i < optionContentBox.children.length; i++) {
            optionContentBox.removeChild(optionContentBox.children[i]);
        }
        // console.log(`this.getTableHTML(this.data)`, this.getTableHTML(this.data));
        optionContentBox.innerHTML = this.getTableHTML(this.data);
    }

    getData() {
        const deletedOptionGroupIds = this.getDeletedOptionGroupIds();
        const deletedOptionItemListItemNumbers = this.getDeletedOptionItemListItemNumbers();

        // console.log('deletedOptionGroupIds', deletedOptionGroupIds);
        // console.log('deletedOptionItemListItemNumbers', deletedOptionItemListItemNumbers);
        this.data.optionGroupDeleteIds = deletedOptionGroupIds;
        this.data.optionItemListDeleteItemNumbers = deletedOptionItemListItemNumbers;

        console.log('@getData().data', this.data);
        return this.data;
    }
}