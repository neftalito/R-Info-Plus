## cantFloresBolsa
```
proceso cantFloresBolsa(ES resultado : numero)
    comenzar
        mientras(HayFlorEnLaBolsa)
            depositarFlor
            resultado := resultado + 1
        repetir(resultado)
            tomarFlor
    fin
```

## cantPapelesBolsa
```
proceso cantPapelesBolsa(ES resultado : numero)
    comenzar
        mientras(HayPapelEnLaBolsa)
            depositarPapel
            resultado := resultado + 1
        repetir(resultado)
            tomarPapel
    fin
```

## izquierda
```
proceso izquierda
    comenzar
        repetir(3)
            derecha
    fin
```

## Mod
```
proceso mod (E a : numero; E b : numero; ES resultado : numero)
    comenzar
        resultado := (a-(b*(a/b)))
    fin
```