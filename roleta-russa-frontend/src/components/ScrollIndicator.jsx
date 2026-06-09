import "../styles/components/misc.css";

function ScrollIndicator() {
  const scrollToFooter = () => {
    const footer = document.querySelector(".footer");
    if (footer) {
      footer.scrollIntoView({ behavior: "smooth" });
    }
  };

  return (
    <div className="scroll-indicator" onClick={scrollToFooter}>
      <span className="scroll-indicator__text">Rolar para baixo</span>
      <div className="scroll-indicator__mouse">
        <div className="scroll-indicator__dot"></div>
      </div>
    </div>
  );
}

export default ScrollIndicator;
