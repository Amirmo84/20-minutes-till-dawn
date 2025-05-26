#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform vec2 u_center;      // Center of the light (in screen coordinates)
uniform float u_radius;     // Radius of the light
uniform vec3 u_lightColor;  // Color of the light (usually white)

varying vec2 v_texCoords;

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);

    vec2 pixelPos = gl_FragCoord.xy;
    float dist = distance(pixelPos, u_center);

    float intensity = smoothstep(u_radius, 0.0, dist);

    vec3 finalColor = mix(texColor.rgb * 0.2, texColor.rgb * u_lightColor, intensity);

    gl_FragColor = vec4(finalColor, texColor.a);
}