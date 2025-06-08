package com.ssafy.project.util;

import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component
public class ChosungSearch {
	private static TreeMap<Character, Integer> CHOSUNG = new TreeMap<>();
	private static TreeMap<Integer, Character> CHOSUNGMAP = new TreeMap<>();

	private ChosungSearch() {
		// 수정된 CHOSUNG 및 CHOSUNGMAP 초기화 (총 19개 초성)
		CHOSUNG.put('ㄱ', 0);
		CHOSUNG.put('ㄲ', 1);
		CHOSUNG.put('ㄴ', 2);
		CHOSUNG.put('ㄷ', 3);
		CHOSUNG.put('ㄸ', 4);
		CHOSUNG.put('ㄹ', 5);
		CHOSUNG.put('ㅁ', 6);
		CHOSUNG.put('ㅂ', 7);
		CHOSUNG.put('ㅃ', 8);
		CHOSUNG.put('ㅅ', 9);
		CHOSUNG.put('ㅆ', 10);
		CHOSUNG.put('ㅇ', 11);
		CHOSUNG.put('ㅈ', 12);
		CHOSUNG.put('ㅉ', 13);
		CHOSUNG.put('ㅊ', 14);
		CHOSUNG.put('ㅋ', 15);
		CHOSUNG.put('ㅌ', 16);
		CHOSUNG.put('ㅍ', 17);
		CHOSUNG.put('ㅎ', 18);

		CHOSUNGMAP.put(0, '가');   // ㄱ
		CHOSUNGMAP.put(1, '까');   // ㄲ
		CHOSUNGMAP.put(2, '나');   // ㄴ
		CHOSUNGMAP.put(3, '다');   // ㄷ
		CHOSUNGMAP.put(4, '따');   // ㄸ
		CHOSUNGMAP.put(5, '라');   // ㄹ
		CHOSUNGMAP.put(6, '마');   // ㅁ
		CHOSUNGMAP.put(7, '바');   // ㅂ
		CHOSUNGMAP.put(8, '빠');   // ㅃ
		CHOSUNGMAP.put(9, '사');   // ㅅ
		CHOSUNGMAP.put(10, '싸');  // ㅆ
		CHOSUNGMAP.put(11, '아');  // ㅇ
		CHOSUNGMAP.put(12, '자');  // ㅈ
		CHOSUNGMAP.put(13, '짜');  // ㅉ
		CHOSUNGMAP.put(14, '차');  // ㅊ
		CHOSUNGMAP.put(15, '카');  // ㅋ
		CHOSUNGMAP.put(16, '타');  // ㅌ
		CHOSUNGMAP.put(17, '파');  // ㅍ
		CHOSUNGMAP.put(18, '하');  // ㅎ
		CHOSUNGMAP.put(19, (char) ('힣' + 1)); // 범위 끝
	}
	public ChosungSearchData process(String title) {
		char[] titleArr = title.toCharArray();
		TreeMap<Integer, Integer> chosungMap = new TreeMap<>();

		for (int i = 0; i < titleArr.length; ++i) {
			Integer index = CHOSUNG.get(titleArr[i]);
			if (index != null) {
				titleArr[i] = '_';
				chosungMap.put(i, index);
			}
		}

		ChosungSearchData data = new ChosungSearchData();
		data.titleArr = titleArr;
		data.chosungMap = chosungMap;

		return data;
	}

	public static TreeMap<Character, Integer> getChosung() {
		return CHOSUNG;
	}

	public static TreeMap<Integer, Character> getChosungMap() {
		return CHOSUNGMAP;
	}
	
	

    /**
     * text 안에 patternData에 담긴 패턴이 포함되어 있는지 검사
     * @param text 원본 문자열(공지 제목)
     * @param patternData ChosungSearch.process() 결과(패턴 char[] + 초성위치 맵)
     * @return 포함 여부
     */
    public boolean contains(String text, ChosungSearchData patternData) {
        char[] pattern = patternData.titleArr;
        TreeMap<Integer, Integer> chosungMap = patternData.chosungMap;
        TreeMap<Integer, Character> choMap = ChosungSearch.getChosungMap();

        int[] lps = computeLps(pattern, chosungMap, choMap);

        int i = 0, j = 0;
        final int N = text.length(), M = pattern.length;
        while (i < N) {
            char tc = text.charAt(i);
            boolean match;

            if (pattern[j] == '_') {
                // 1) 먼저 “text가 자모(초성)”인지 확인
                Integer textChosungIdx = CHOSUNG.get(tc);
                int patChosungIdx = chosungMap.get(j);

                if (textChosungIdx != null) {
                    // text가 자모인 경우: 인덱스가 동일해야 매칭
                    match = (textChosungIdx == patChosungIdx);
                } else {
                    // text가 완성형 한글인 경우: 기존 방법 (범위 비교)
                    char start = choMap.get(patChosungIdx);
                    char end   = choMap.get(patChosungIdx + 1);
                    match = (tc >= start && tc < end);
                }
            } else {
                // 패턴 문자가 '_'가 아니라면, 문자 그대로 비교
                match = (tc == pattern[j]);
            }

            if (match) {
                i++; 
                j++;
                if (j == M) {
                    return true; // 전체 패턴 일치
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return false;
    }

    private int[] computeLps(char[] pattern,
                             TreeMap<Integer, Integer> chosungMap,
                             TreeMap<Integer, Character> choMap) {
        int M = pattern.length;
        int[] lps = new int[M];
        int len = 0, i = 1;

        while (i < M) {
            char pi = pattern[i];
            char pl = pattern[len];
            boolean match = false;

            // --- (A) pi와 pl이 완전히 같은 문자인 경우 ---
            if (pi == pl && pi != '_') {
                // 둘 다 일반 문자(‘_’가 아닌 동질 문자)
                match = true;

                // (A1) “둘 다 '_'(placeholder)”인 경우: 
                // 이 분기문엔 들어오지 않음. pi==pl=='_'인 경우는 아래 (B)에서 처리한다.
            }
            // --- (B) pi == '_' && pl == '_' 두 placeholder끼리 비교 ---
            else if (pi == '_' && pl == '_') {
                // pattern[i]과 pattern[len] 둘 다 placeholder니까
                // 그 밑에 저장된 chosungMap 인덱스를 비교해야 한다.
                int idxI = chosungMap.get(i);
                int idxL = chosungMap.get(len);
                match = (idxI == idxL);
            }
            // --- (C) pi == '_' && pl는 자모(초성) 직접 나올 때 ---
            else if (pi == '_' && CHOSUNG.containsKey(pl)) {
                // pattern[i] = '_' → 원래 인덱스 = chosungMap.get(i)
                // pl(=pattern[len])는 자모 이므로 CHOSUNG.get(pl)로 인덱스 추출
                int idxI = chosungMap.get(i);
                int idxL = CHOSUNG.get(pl);
                match = (idxI == idxL);
            }
            // --- (D) pl == '_' && pi는 자모(초성) 직접 나올 때 ---
            else if (pl == '_' && CHOSUNG.containsKey(pi)) {
                int idxL = chosungMap.get(len);
                int idxI = CHOSUNG.get(pi);
                match = (idxI == idxL);
            }
            // --- (E) pi == '_' && pl는 완성형 한글일 때 기존 범위 비교 ---
            else if (pi == '_' && pl >= 0xAC00 && pl <= 0xD7A3) {
                int idxI = chosungMap.get(i);
                char start = choMap.get(idxI), end = choMap.get(idxI + 1);
                match = (pl >= start && pl < end);
            }
            // --- (F) pl == '_' && pi는 완성형 한글일 때 기존 범위 비교 ---
            else if (pl == '_' && pi >= 0xAC00 && pi <= 0xD7A3) {
                int idxL = chosungMap.get(len);
                char start = choMap.get(idxL), end = choMap.get(idxL + 1);
                match = (pi >= start && pi < end);
            }
            // --- 그 외엔 불일치 ---
            else {
                match = false;
            }

            if (match) {
                lps[i++] = ++len;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                    // len이 바뀌었으니, i는 그대로 두고 다시 비교
                } else {
                    lps[i++] = 0;
                }
            }
        }
        return lps;
    }
}
