animate('.moko-404', {
  timings: '2 0.05 0.1 0.0 1.3 0'.split(' '),
  frames: 5,
  frameNames: 'leafO stemO leafN stemN leafM stemM leafL stemL leafK stemK leafJ stemJ leafI stemI leafH leafG stemG leafF stemF leafE stemE leafD stemD leafC stemC leafB stemB leafA stemA eyeL eyeR pupilL pupilR'.split(' '),
  repeat: true,
  close: true });


function animate(containerSelector, { repeat = 0, timings = [1], frameNames = ['frame'], frames = 2, close = false } = {}) {
  if (repeat === true) {repeat = Infinity;}
  const container = $(containerSelector);
  const styleElt = document.createElement('style');
  let styles = '';

  container.addClass('svg-animated');
  MorphSVGPlugin.convertToPath(`${containerSelector} circle,
                                ${containerSelector} rect,
                                ${containerSelector} ellipse,
                                ${containerSelector} line,
                                ${containerSelector} polygon,
                                ${containerSelector} polyline`);

  frameNames.forEach(frameName => {
    const tl = new TimelineMax({ repeat });
    const firstFrame = container.find(`.${frameName}1`)[0];
    let i;
    let previousFrame = firstFrame;

    for (i = 2; i <= frames; i += 1) {
      const className = `.${frameName}${i}`;
      const frame = container.find(className);
      styles += `${className}{visibility:hidden;}`;
      tl.to(firstFrame, timing(i), { morphSVG: frame.length ? frame : previousFrame }, "+=0");
      previousFrame = frame;
    }
    styles += `.${frameName}1{visibility:visible;}`;

    if (close) {
      tl.to(firstFrame, timing(i), { morphSVG: firstFrame }, "+=0");
    }

    styleElt.innerHTML += styles;
    container.prepend(styleElt);
  });

  function timing(i) {
    return timings[Math.min(i - 2, timings.length - 1)];
  }
}