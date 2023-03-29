;ANSIで保存したらできた


;%<\AcVar.17.0 Lisp.0mybldg_更新日>%
(setq 0mybldg_更新日 "2023/3/23　見積図")
;%<\AcVar.17.0 Lisp.0mybldg_物件名>%
(setq 0mybldg_物件名 "TSロジテック新三芳センター")

;ジョイント位置関係
;%<\AcVar.17.0 Lisp.0mybldg_剛接合ジョイント位置t>%
(setq 0mybldg_剛接合ジョイント位置t "柱心より1200")
;%<\AcVar.17.0 Lisp.0mybldg_柱ジョイント位置>%
(setq 0mybldg_柱ジョイント位置 "3FL+1000")


;設計GL関係

;%<\AcVar.17.0 Lisp.0mybldg_1FLto設計GL>%
(setq 0mybldg_1FLto設計GL "設計GL+1000")
;%<\AcVar.17.0 Lisp.0mybldg_1FLtoSL>%
(setq 0mybldg_1FLtoSL "FL±0")
;%<\AcVar.17.0 Lisp.0mybldg_1FLtoBPL>%
(setq 0mybldg_1FLtoBPL "1FL-250")
;%<\AcVar.17.0 Lisp.0mybldg_本柱モルタル厚>%
(setq 0mybldg_本柱モルタル厚 "50mm")
;%<\AcVar.17.0 Lisp.0mybldg_間柱モルタル厚>%
(setq 0mybldg_間柱モルタル厚 "30mm")


;スロープ棟の設計GL関係
;%<\AcVar.17.0 Lisp.1mybldg_1FLto設計GL>%
;(setq 1mybldg_1FLto設計GL "設計GL+100")
;スロープ棟のBPL下端とりあえず1000+250
;%<\AcVar.17.0 Lisp.1mybldg_1FLtoBPL>%
(setq 1mybldg_1FLtoBPL "1FL-1250")


;下端関係

;%<\AcVar.17.0 Lisp.0mybldg_基礎梁下端>%
(setq 0mybldg_基礎梁下端 "1FL-1800")
;%<\AcVar.17.0 Lisp.0mybldg_基礎下端>%
(setq 0mybldg_基礎下端 "1FL-2000")
;1階バース部の1m下がりの基礎梁下端
;%<\AcVar.17.0 Lisp.0mybldg_カスタム下端1>%
(setq 0mybldg_カスタム下端1 "1FL-2800")
;1階バース部の1m下がりの基礎下端
;%<\AcVar.17.0 Lisp.0mybldg_カスタム下端2>%
(setq 0mybldg_カスタム下端2 "1FL-3000")
;%<\AcVar.17.0 Lisp.0mybldg_カスタム下端3>%
(setq 0mybldg_カスタム下端3 "1FL-1200")
;スロープ部の基礎下端 1000+300+900+1200
;%<\AcVar.17.0 Lisp.0mybldg_カスタム下端4>%
(setq 0mybldg_カスタム下端4 "1FL-2400")
;スロープ部の基礎基礎梁下端 1000+300+900
;%<\AcVar.17.0 Lisp.0mybldg_カスタム下端5>%
(setq 0mybldg_カスタム下端5 "1FL-2200")

;上端関係

;%<\AcVar.17.0 Lisp.0mybldg_基礎梁上端>%
(setq 0mybldg_基礎梁上端 "1FL-300")
;%<\AcVar.17.0 Lisp.0mybldg_デッキスラブ上端>%
(setq 0mybldg_デッキスラブ上端 "FL-20")
;%<\AcVar.17.0 Lisp.0mybldg_梁上端>%
(setq 0mybldg_梁上端 "FL-200")
;1階EVピットのスラブ天端レベル
;%<\AcVar.17.0 Lisp.0mybldg_カスタム上端1>%
(setq 0mybldg_カスタム上端1 "1FL-1900")
;1階バース部の1m下がりのスラブ上端
;%<\AcVar.17.0 Lisp.0mybldg_カスタム上端2>%
(setq 0mybldg_カスタム上端2 "1FL-1000")
;1階バース部の1m下がりの基礎梁上端
;%<\AcVar.17.0 Lisp.0mybldg_カスタム上端3>%
(setq 0mybldg_カスタム上端3 "1FL-1300")

;スロープ棟基礎スラブ上端レベル
;%<\AcVar.17.0 Lisp.0mybldg_カスタム上端4>%
(setq 0mybldg_カスタム上端4 "1FL-1000")
;スロープ棟基礎梁上端レベル
;%<\AcVar.17.0 Lisp.0mybldg_カスタム上端5>%
(setq 0mybldg_カスタム上端5 "1FL-1300")
;スロープ棟2Fスラブ上端レベル
;%<\AcVar.17.0 Lisp.0mybldg_カスタム上端6>%
(setq 0mybldg_カスタム上端6 "2FL-20")
;スロープ棟基礎梁上端レベル
;%<\AcVar.17.0 Lisp.0mybldg_カスタム上端7>%
(setq 0mybldg_カスタム上端7 "2FL-200")
