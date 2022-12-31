class OptionManager {
    data;

    constructor() {
        // 예시 데이터 구조
        this.data = {
            isOption: 'false',
            optionType: 'COMBINATION',
            optionGroupList: [
                {
                    name: '색상',
                    value: '빨강,파랑,초록',
                },
                {
                    name: '사이즈',
                    value: 's,m,l',
                },
            ],
            optionItemList: [
                {
                    values: ['빨강', 's'],
                    price: 50000,
                    qty: 3,
                    status: 'SALE',
                },
                {
                    values: ['빨강', 'm'],
                    price: 50001,
                    qty: 31,
                    status: 'SALE',
                },
                {
                    values: ['빨강', 'l'],
                    price: 50002,
                    qty: 32,
                    status: 'SALE',
                },
                {
                    values: ['파랑', 's'],
                    price: 50000,
                    qty: 3,
                    status: 'SALE',
                },
                {
                    values: ['파랑', 'm'],
                    price: 50001,
                    qty: 31,
                    status: 'SALE',
                },
                {
                    values: ['파랑', 'l'],
                    price: 50002,
                    qty: 32,
                    status: 'SALE',
                },
                {
                    values: ['초록', 's'],
                    price: 50000,
                    optionQty: 3,
                    status: 'SALE',
                },
                {
                    values: ['초록', 'm'],
                    price: 50001,
                    qty: 31,
                    status: 'SALE',
                },
                {
                    values: ['초록', 'l'],
                    price: 50002,
                    qty: 32,
                    status: 'SALE',
                },
            ],
        };
    }

    tryInit() {
        const jsonString = document.getElementById('option-manager-init-data').value;
        if (typeof jsonString !== 'string' || jsonString === '') {
            this.render();
            return;
        }

        try {
            const json = JSON.parse(jsonString);
            this.data = json;
            this.render();
        } catch (e) {
            console.error('e', e);
            this.render();
        }
    }

    changeOptionFlag(target) {
        // console.log('target', target);
        const value = target.value;
        if (typeof value !== 'string') {
            return;
        }
        this.data.isOption = value;
        if (this.data.isOption === "true") {
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
        this.data.optionGroupList.splice(index, 1);
        this.render();
    }

    addOptionItem() {
        if (this.data.optionType === "SIMPLE") {
            if (this.data.optionGroupList.length >= 1) {
                alert('단독형인 경우에 옵션은 1개까지만 등록 가능합니다. 옵션을 여러개 등록하기 위해선 조합형을 선택해주세요.');
                return;
            }
        }

        this.data.optionGroupList.push({
            name: '',
            value: '',
        });
        this.render();
    }

    changeOptionItemName(index, target) {
        this.data.optionGroupList[index].name = target.value;
    }

    changeOptionItemValue(index, target) {
        this.data.optionGroupList[index].value = target.value;
    }

    applyOptionList() {
        if (!confirm('옵션 목록으로 적용하면 옵션 목록이 전부 초기화 됩니다. 계속 진행하시겠습니까?')) {
            return;
        }
        const all_list_option = d3.cross(...this.data.optionGroupList.map(item => item.value.split(',')));
        // console.log('all_list_option', all_list_option);
        this.data.optionItemList = all_list_option.map((item) => {
            return {
                values: item,
                price: 0,
                qty: 0,
                status: 'SALE',
            };
        });
        // this.data.option_list =
        this.render();
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
                            <input ${data.isOption === "true" ? "checked " : ""}class="inline-flex" type="radio" name="option_flag" value="true" id="option_flag_on" onchange="optionManager.changeOptionFlag(this)" />
                            <label class="inline-flex font-normal" for="option_flag_on">&nbsp;설정함</label>
                        </div>
                        <div class="inline-flex items-center items-center">
                            <input ${data.isOption === "false" ? "checked " : ""}class="inline-flex" type="radio" name="option_flag" value="false" id="option_flag_off" onchange="optionManager.changeOptionFlag(this)" />
                            <label class="inline-flex font-normal" for="option_flag_off">&nbsp;설정안함</label>
                        </div>
                    </div>
                </div>
                <div class="w-full" id="option-detail-control-form-area" style="display: ${this.data.isOption === 'true' ? 'block' : 'none'}">
                    <div class="w-full h-px bg-slate-200 my-2"></div>
                    <div data-name="form-row" class="w-full flex flex-wrap">
                        <div class="flex flex-wrap items-start content-start" style="width: 140px;">
                            옵션 구성타입
                        </div>
                        <div class="flex flex-wrap items-start content-start" style="width: calc(100% - 140px);">
                            <div class="inline-flex items-center items-center mr-2">
                                <input ${data.optionType === "SIMPLE" ? "checked " : ""}class="inline-flex" type="radio" name="option_compose_type" value="SIMPLE" id="option_compose_type_single" onchange="optionManager.changeOptionComposeType(this)" />
                                <label class="inline-flex font-normal" for="option_compose_type_single">&nbsp;단독형</label>
                            </div>
                            <div class="inline-flex items-center items-center">
                                <input ${data.optionType === "COMBINATION" ? "checked " : ""}class="inline-flex" type="radio" name="option_compose_type" value="COMBINATION" id="option_compose_type_multiple" onchange="optionManager.changeOptionComposeType(this)" />
                                <label class="inline-flex font-normal" for="option_compose_type_multiple">&nbsp;조합형</label>
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
                                                <tr class="option-item-row" data-index="${index}">
                                                    <td>
                                                        <input name="option_name" type="text" placeholder="예시) 색상" value="${item.name}" onchange="optionManager.changeOptionItemName(${index}, this)" />
                                                    </td>
                                                    <td>
                                                        <input name="option_values" type="text" placeholder="예시) 빨강,파랑,초록,분홍" style="width: 100%;" value="${item.value}" onchange="optionManager.changeOptionItemValue(${index}, this)" />
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
                                                <tr class="option-list-item-row" data-index="${index}">
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
                                                        <input ${data.optionType === "SIMPLE" ? "disabled " : ""}type="text" value="${item.price}" onchange="optionManager.changeOptionListRowPrice(${index}, this)" />
                                                    </td>
                                                    <td>
                                                        <input type="number" value="${item.qty}" onchange="optionManager.changeOptionListRowInventoryCount(${index}, this)" />
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
        return this.data;
    }
}