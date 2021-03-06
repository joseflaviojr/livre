/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_joseflavio_tqc_console_NativoConsole */

#ifndef _Included_com_joseflavio_tqc_console_NativoConsole
#define _Included_com_joseflavio_tqc_console_NativoConsole
#ifdef __cplusplus
extern "C" {
#endif
#undef com_joseflavio_tqc_console_NativoConsole_COR_PRETA
#define com_joseflavio_tqc_console_NativoConsole_COR_PRETA 0L
#undef com_joseflavio_tqc_console_NativoConsole_COR_AZUL
#define com_joseflavio_tqc_console_NativoConsole_COR_AZUL 1L
#undef com_joseflavio_tqc_console_NativoConsole_COR_VERDE
#define com_joseflavio_tqc_console_NativoConsole_COR_VERDE 2L
#undef com_joseflavio_tqc_console_NativoConsole_COR_CIANO
#define com_joseflavio_tqc_console_NativoConsole_COR_CIANO 3L
#undef com_joseflavio_tqc_console_NativoConsole_COR_VERMELHA
#define com_joseflavio_tqc_console_NativoConsole_COR_VERMELHA 4L
#undef com_joseflavio_tqc_console_NativoConsole_COR_MAGENTA
#define com_joseflavio_tqc_console_NativoConsole_COR_MAGENTA 5L
#undef com_joseflavio_tqc_console_NativoConsole_COR_MARRON
#define com_joseflavio_tqc_console_NativoConsole_COR_MARRON 6L
#undef com_joseflavio_tqc_console_NativoConsole_COR_CINZA_INTENSA
#define com_joseflavio_tqc_console_NativoConsole_COR_CINZA_INTENSA 7L
#undef com_joseflavio_tqc_console_NativoConsole_COR_CINZA_ESCURA
#define com_joseflavio_tqc_console_NativoConsole_COR_CINZA_ESCURA 8L
#undef com_joseflavio_tqc_console_NativoConsole_COR_AZUL_INTENSA
#define com_joseflavio_tqc_console_NativoConsole_COR_AZUL_INTENSA 9L
#undef com_joseflavio_tqc_console_NativoConsole_COR_VERDE_INTENSA
#define com_joseflavio_tqc_console_NativoConsole_COR_VERDE_INTENSA 10L
#undef com_joseflavio_tqc_console_NativoConsole_COR_CIANO_INTENSA
#define com_joseflavio_tqc_console_NativoConsole_COR_CIANO_INTENSA 11L
#undef com_joseflavio_tqc_console_NativoConsole_COR_VERMELHA_INTENSA
#define com_joseflavio_tqc_console_NativoConsole_COR_VERMELHA_INTENSA 12L
#undef com_joseflavio_tqc_console_NativoConsole_COR_MAGENTA_INTENSA
#define com_joseflavio_tqc_console_NativoConsole_COR_MAGENTA_INTENSA 13L
#undef com_joseflavio_tqc_console_NativoConsole_COR_AMARELA
#define com_joseflavio_tqc_console_NativoConsole_COR_AMARELA 14L
#undef com_joseflavio_tqc_console_NativoConsole_COR_BRANCA
#define com_joseflavio_tqc_console_NativoConsole_COR_BRANCA 15L
/*
 * Class:     com_joseflavio_tqc_console_NativoConsole
 * Method:    getTotalColunas
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_joseflavio_tqc_console_NativoConsole_getTotalColunas
  (JNIEnv *, jobject);

/*
 * Class:     com_joseflavio_tqc_console_NativoConsole
 * Method:    getTotalLinhas
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_joseflavio_tqc_console_NativoConsole_getTotalLinhas
  (JNIEnv *, jobject);

/*
 * Class:     com_joseflavio_tqc_console_NativoConsole
 * Method:    setCorTexto
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_joseflavio_tqc_console_NativoConsole_setCorTexto
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_joseflavio_tqc_console_NativoConsole
 * Method:    getCorTexto
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_joseflavio_tqc_console_NativoConsole_getCorTexto
  (JNIEnv *, jobject);

/*
 * Class:     com_joseflavio_tqc_console_NativoConsole
 * Method:    setCorTextoFundo
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_joseflavio_tqc_console_NativoConsole_setCorTextoFundo
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_joseflavio_tqc_console_NativoConsole
 * Method:    getCorTextoFundo
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_joseflavio_tqc_console_NativoConsole_getCorTextoFundo
  (JNIEnv *, jobject);

/*
 * Class:     com_joseflavio_tqc_console_NativoConsole
 * Method:    limpar
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_joseflavio_tqc_console_NativoConsole_limpar
  (JNIEnv *, jobject);

/*
 * Class:     com_joseflavio_tqc_console_NativoConsole
 * Method:    esperar
 * Signature: (Z)C
 */
JNIEXPORT jchar JNICALL Java_com_joseflavio_tqc_console_NativoConsole_esperar
  (JNIEnv *, jobject, jboolean);

/*
 * Class:     com_joseflavio_tqc_console_NativoConsole
 * Method:    setTelaCheia
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_joseflavio_tqc_console_NativoConsole_setTelaCheia
  (JNIEnv *, jobject, jboolean);

#ifdef __cplusplus
}
#endif
#endif
