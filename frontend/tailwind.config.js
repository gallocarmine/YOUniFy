/** @type {import('tailwindcss').Config} */
export default {
  // --- Content Configuration ---
  // Definisce i percorsi dei file che Tailwind deve scansionare per generare il CSS.
  // Il motore JIT (Just-In-Time) guarderà in questi file: se trova una classe (es. 'bg-red-500'),
  // genererà il CSS corrispondente. Se una classe non è usata qui, non finirà nel file finale.
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],

  // --- Theme Customization ---
  theme: {
    // Usiamo 'extend' per AGGIUNGERE le nostre personalizzazioni ai default di Tailwind.
    // Se scrivessimo queste configurazioni fuori da 'extend', sovrascriveremmo completamente
    // i default (es. perderemmo tutti i colori standard di Tailwind).
    extend: {

      // Personalizzazione Font
      fontFamily: {
        // Sovrascrivendo la chiave 'sans', impostiamo 'Outfit' come font predefinito
        // per tutte le classi 'font-sans' (e quindi per il body, se impostato nel CSS base).
        sans: ['Outfit', 'sans-serif'],
      },

      // Design Tokens (Colori del Brand)
      colors: {
        // Oggetto colori strutturato: genera classi come 'bg-younify-blue', 'text-younify-dark', ecc.
        younify: {
          blue: '#1d4ed8', // Colore primario (Accent)
          dark: '#121212', // Background scuro principale
          light: '#fafafa', // Testo chiaro / Background alternativo
        },
        // Alias diretto per compatibilità con codice esistente ('text-younify-blue')
        'younify-blue': '#1d4ed8',
      },

      // Ombre personalizzate (Effect Styles)
      boxShadow: {
        // Effetto 'Water/Glass': Simula una superficie liquida o vetrosa 3D.
        // È composto da 3 livelli sovrapposti:
        'water': [
          // 1. Luce speculare interna (Top-Left): Simula il riflesso della luce sulla superficie curva.
          'inset 4px 4px 15px rgba(255, 255, 255, 0.9)',
          // 2. Ombra interna (Bottom-Right): Conferisce volume e profondità all'oggetto.
          'inset -4px -4px 15px rgba(0, 0, 0, 0.05)',
          // 3. Ombra esterna (Drop Shadow): Stacca l'elemento dallo sfondo.
          '5px 5px 20px rgba(0, 0, 0, 0.1)',
        ].join(', '),
      },

      // Animazioni personalizzate
      animation: {
        // Estende le animazioni base. Qui rallentiamo l'animazione 'spin' standard
        // portandola a 8 secondi per un effetto rotatorio decorativo lento.
        'spin-slow': 'spin 8s linear infinite',
      },
    },
  },

  // Plugin esterni (vuoto per ora)
  plugins: [],
}