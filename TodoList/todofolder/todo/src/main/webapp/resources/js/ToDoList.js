const mywork = document.getElementsByName("mywork");
const inputbtn = document.getElementById("inputbtn"); 
const reset = document.querySelector(".reset");
const li = document.querySelector("li");
const minreset = document.querySelector("#minreset");

const inputtext = document.querySelector(".inputtext");
const fomr2 = document.getElementById("form2");
const worklist = document.querySelector(".worklist");


//todo 삽입
function todoplus() {
console.log("loginmember" + loginmember);
    if(loginmember == null || loginmember == "") {
        alert("로그인이 필요한 서비스 입니다.");
    }else if(inputtext.value.length > 0) {
        // worklist.append(checkbtn);
        form2.innerHTML += "<div class='worklist'> <button type='button' id='checkbtn' style='background-Color: transparent; border: none' class='fa-regular fa-circle-check'>" + "</button>"
                + "<span id='todo'>" + inputtext.value + "</span>" + "<button type='button' id='minreset' style='background-color: transparent; border: none;' class='fa-solid fa-trash-can'>" + "</button> </div>" ;
        // worklist.append(minreset);      
    }

    $.ajax({
            url : "todoInsert",
            data : {"inputtext" : inputtext.value,
                    "memberNo" : memberNo},
            type : "POST",
    
            success : function(text){ 

                console.log("inputtext: " + inputtext.value);
                console.log("데이터 삽입 성공"); 
                inputtext.value = "";
            },error : function(){
                console.log("todoInsert 오류발생");
            }
        });
};


//todo 조회
function todoSelect() {
    $.ajax({
        url : contextPath + "/main/todoList",
        data : {"memberNo" : memberNo},
        dataType:"JSON",

        success : function(todoList){ 

            console.log("todoSelect 성공"); 
            
            const form2 = document.getElementById("form2");

            for (var i = 0; i < todoList.length; i++) {
                var inputtext = todoList[i].inputtext;
                // inputtext를 사용하여 원하는 작업 수행
                console.log("inputtext: ", inputtext);

                // var selectDiv = document.createElement("div");
                // selectDiv.textContent = inputtext;
                // form2.appendChild(selectDiv);

                var selectDiv =  "<div class='worklist'>"
                + "<button type='button' id='checkbtn' style='background-Color: transparent; border: none' class='fa-regular fa-circle-check'></button>"
                + "<span id='todo'>" + inputtext + "</span>"
                + "<button type='button' id='minreset' style='background-color: transparent; border: none;' class='fa-solid fa-trash-can'></button>"
                + "</div>";

                form2.innerHTML += selectDiv;
                
            }
            
           
        },error : function(req, status, error){
            console.log("todoSelect 오류발생");
            console.log("code: " + req.status + "\n" + "message: " + req.responseText + "\n" + "error: " + error);
        }
    });
}

todoSelect();


//클릭시 글씨 색상 변환 (구글에 색상선택도구 검색)
$(document).on("click", "#checkbtn", function(e) {
    console.log($(e.target));
    $(e.target).next().css("color", "#dddddd");
    $(e.target).next().css("text-decoration", "line-through");

});

//전체삭제
reset.addEventListener("click", function() {
    form2.innerHTML = '';

    $.ajax({
        url : "todoAllDelete",
        data : {"memberNo" : memberNo},
        type : "POST",

        success : function(text){ 

            console.log("todoAllDelete 성공"); 
            inputtext.value = "";
        },error : function(){
            console.log("todoAllDelete 오류발생");
        }
    });

});

// $(document).on("click", "#minreset", function(e) {
//     const checkDelete = $(e.target).parent().remove();

// });

//선택삭제
$(document).on("click", "#minreset", function(e) {
    // const todoNo = parseInt("${loginMember.todoNo}", 10);   //parsInt("변환할 문자", 변환하고싶은 진법);  
                                                            //ex)parsInt("123", 10); -> 문자123을 10진법으로 변환

    var selectedTodoNo = $(e.target).closest(".worklist").find("#todo").text(); // 선택한 todo 가져오기
    console.log("선택한 inputtext: " + selectedTodoNo);
    
    $.ajax({
        url : contextPath + "/main/todoList",
        data : {"memberNo" : memberNo},
        dataType:"JSON",

        success : function(todoList){ 

            console.log("todoDelete(todoSelect) 성공"); 
            
            for (var i = 0; i < todoList.length; i++) {

                var todoNos = todoList[i].todoNo;   //todoNo 모두 가져오기
                //console.log("todoNo : " + todoNos);

                var inputtext = todoList[i].inputtext;  //todo inputtext 모두 가져오기

                if (inputtext === selectedTodoNo) { //선택한 todo와 todo inputtext 중 같은 inputtext인것 찾기
                    console.log("선택한 todoNo: " + todoNos);   //선택한 todoNo 출력

                    $.ajax({
                        url : "todoDelete",
                        data : {"todoNo" : todoNos},
                        type : "POST",
                
                        success : function(result){ 
                
                            console.log("todoDelete 성공"); 
                            
                        },error : function(){
                            console.log("todoDelete 오류발생");
                        }
                    });
                }
            }
        },error: function() {
            console.log("todoDelete(todoSelect) 실패");
        }
    });
    $(e.target).parent().remove();  //선택삭제
});



