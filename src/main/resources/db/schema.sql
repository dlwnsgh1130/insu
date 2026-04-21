-- 인덱스
-- 데이터를 빠르게 찾기 위한 목차(색인)
-- 인덱스가 없으면: full scan (500건이하도 괜찮음)
-- 인덱스가 있으면: Index scan -> 오라클 옵티마이저(내비게이션)가 인덱스 사용 여부를 자동 판단


-- 단점: 조회 외는 성능이 떨어짐. 너무 많이 만들면 저장공간을 차지 하기 때문에 성능 저하


-- B-Tree 구조 탐색
-- root             ->             [50]
-- branch           ->     [20,30]      [70,80]
-- leaf(컬럼값+ROWID)->  [10][25][35]  [60][75][85]


-- CREATE [UNIQUE] INDEX 인덱스명 ON 테이블명(컬럼명[, 컬럼명, ... ]);


-- 단일 컬럼 인덱스
-- 보험사별 조회: SELECT * FROM PRODUCTS WHERE COMPANY = '삼성화재';
CREATE INDEX IDX_PRODUCTS_COMPANY ON PRODUCTS(COMPANY);
-- 카테고리별 조회: WHERE CATEGORY = "생명보험"
CREATE INDEX IDX_PRODUCTS_CATEGORY ON PRODUCTS(CATEGORY);


-- 실행 계획 확인을 위한 쿼리
EXPLAIN PLAN FOR
SELECT * FROM PRODUCTS WHERE CATEGORY = '생명보험';
-- 결과 출력
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);


-- 보험료 범위 조회: WHERE PREMIUM BETWEEN 10000 AND 50000;
CREATE INDEX IDX_PRODUCTS_PREMIUM ON PRODUCTS(PREMIUM);




-- 평균 내림차순 정렬
-- 인덱스 탐색 속도 증가. 미리 내부적으로 정렬을 했기 때문에  FULL TABLE SCAN시 발생하는 정렬 비용 제거
CREATE INDEX IDX_PRODUCTS_RATING ON PRODUCTS(RATING DESC);


-- 복합 인덱스 : 두 개 이상의 컬럼을 조합한 인덱스
-- 작성된 컬럼의 순서 중요. 첫번째 컬럼이 선행 컬럼
-- SELECT * FROM PRODUCTS WHERE ACTIVE = 'Y' AND CATEGORY = '건강보험';
-- CREATE INDEX IDX_PRODUCTS_ACTIVE_CATEGORY ON PRODUCTS(ACTIVE, CATEGORY);
CREATE INDEX IDX_PRODUCTS_CATEGORY_ACTIVE ON PRODUCTS(CATEGORY, ACTIVE);


-- 보험사 + 활성 상품 인덱스
CREATE INDEX IDX_PRODUCTS_COMPANY_ACTIVE ON PRODUCTS(COMPANY, ACTIVE);


-- 인덱스 삭제
DROP INDEX IDX_PRODUCTS_ACTIVE_CATEGORY;


-- 인덱스 등록 정보
SELECT INDEX_NAME, COLUMN_NAME
FROM USER_IND_COLUMNS WHERE TABLE_NAME = 'PRODUCTS';




