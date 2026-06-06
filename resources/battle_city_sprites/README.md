# Battle City – Sprite Paketi

Bu paket, yüklediğin Battle City sprite sheet'lerinden kesilmiş, **arka planı saydam (transparent PNG)** sprite'ları içerir. Projeye doğrudan eklenebilir.

## Klasör yapısı

```
battle_city_sprites/
├── tanks/            # Tüm tanklar (16x16, saydam)
│   ├── player1_gold/     # Oyuncu 1 (sarı/altın)
│   ├── player2_green/    # Oyuncu 2 (yeşil)
│   ├── enemy_white/      # Düşman tank (beyaz/gümüş)
│   └── enemy_purple/     # Düşman tank (mor – power-up/yanıp sönen renk)
├── tiles/            # Harita karoları (16x16)
├── items/            # Power-up ikonları, mermiler, patlamalar, kalkan yıldızı
├── backgrounds/      # Hazır stage haritaları (208x208)
└── preview.png       # Tüm sprite'ların önizlemesi
```

## Tank isimlendirmesi

Her tank dosyası şu kalıpta:

```
{palet}_shape{0-7}_{yön}_{kare}.png
```

- **palet**: `player1_gold`, `player2_green`, `enemy_white`, `enemy_purple`
- **shape0–shape7**: 8 farklı tank tasarımı. Orijinal oyunda bunlar
  oyuncu yükseltme seviyeleri (Lv1–4) ve düşman tipleri (Basic / Fast /
  Power / Armor) ile eşleşir. Aynı 8 şekil her 4 palette de bulunur.
- **yön**: `up`, `down`, `left`, `right`
- **kare**: `0` veya `1` → tank hareket animasyonu için iki kare. İkisini
  sırayla göstererek paletlerin yürüme animasyonunu verebilirsin.

Örnek: `player1_gold_shape0_up_0.png` → Oyuncu 1, temel tank, yukarı bakıyor, 1. kare.

> İpucu: Oyuncu için `player1_gold` (Lv1 = shape0), düşmanlar için
> `enemy_white` paletini kullanman yeterli. Diğer paletler/şekiller
> yükseltme ve çeşitlilik için hazırdır.

## Tile'lar

| Dosya | Açıklama |
|-------|----------|
| `brick.png` | Tuğla duvar (yıkılabilir) |
| `steel.png` | Çelik/beton duvar (sert) |
| `water_0.png`, `water_1.png` | Su – iki animasyon karesi |
| `forest.png` | Çalı/orman (üstünden geçilir, gizler) |
| `ice.png` | Buz (kayma) |
| `base_eagle.png` | Üs / kartal (korunan hedef) |
| `base_destroyed.png` | Yıkılmış üs |

Tüm tile'lar 16x16. Battle City'de saha bu karolardan örülür.

## Items

- `powerup_*.png` – helmet (kalkan), timer (dondurma), shovel (üssü güçlendir),
  star (yükseltme), grenade (tümünü yok et), tank_1up (ekstra can), gun (tam yükseltme).
  (İkonların etrafındaki turkuaz çerçeve orijinal sheet'in parçasıdır.)
- `shield_star_0–3.png` – doğuş/kalkan yıldızı animasyonu
- `bullet_0–1.png` – mermi
- `explosion_*.png` – patlama kareleri (küçükten büyüğe)

## Teknik notlar

- Saydamlık: kenardan bağlantılı siyah pikseller saydam yapıldı; tankın
  **iç detayları korundu** (tread boşlukları delik olmadı).
- Çözünürlük: orijinal NES boyutu (16x16). Oyunda büyütmek için
  **nearest-neighbor** ölçekleme kullan (bulanıklaşmaması için).
- Format: PNG, RGBA.

## Atıf

Orijinal grafikler **Battle City – © 1980, 1985 Namco**. Sheet'ler
Zephie87 tarafından "rip" edilmiştir. Bu varlıklar Namco'ya ait olup
yalnızca kişisel/eğitim amaçlı proje için kullanılmalıdır.
